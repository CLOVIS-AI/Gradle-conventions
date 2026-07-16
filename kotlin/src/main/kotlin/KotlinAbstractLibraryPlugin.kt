package dev.opensavvy.conventions.kotlin

import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import kotlinx.kover.gradle.plugin.dsl.GroupingEntityType
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomLicense
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.publish.maven.tasks.GenerateMavenPom
import org.gradle.api.tasks.bundling.Jar
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.parameters.DokkaSourceSetSpec
import java.net.URI

interface LibraryExtension {
	val name: Property<String>
	val description: Property<String>
	val homeUrl: Property<String>
	val license: Property<Action<MavenPomLicense>>
	val coverage: Property<Int>
}

class KotlinAbstractLibraryPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.pluginManager.apply("com.gradleup.nmcp")
		target.pluginManager.apply("maven-publish")
		target.pluginManager.apply("signing")
		target.pluginManager.apply("dev.opensavvy.dokka-mkdocs")
		target.pluginManager.apply("org.jetbrains.dokka")
		target.pluginManager.apply("org.jetbrains.kotlinx.kover")

		val config = target.extensions.create<LibraryExtension>("library")
		config.coverage.convention(0)

		// region Documentation

		target.extensions.configure<DokkaExtension> {
			moduleName.set(config.name)

			dokkaSourceSets.configureEach {
				configureDokkaSourceSet(target, this)
			}
		}

		val documentationJar = target.tasks.register("documentationJar", Jar::class.java) {
			description = "Generate the documentation JAR for MavenCentral"
			group = "publishing"

			from(target.tasks.named("dokkaGeneratePublicationHtml"))
			archiveClassifier.set("javadoc")
		}

		// endregion
		// region GitLab Maven Registry

		target.extensions.configure<PublishingExtension> {
			repositories {
				val projectId = System.getenv("CI_PROJECT_ID") ?: return@repositories
				val token = System.getenv("CI_JOB_TOKEN") ?: return@repositories
				val api = System.getenv("CI_API_V4_URL") ?: return@repositories

				maven {
					name = "GitLab"
					url = URI("$api/projects/$projectId/packages/maven")

					credentials(HttpHeaderCredentials::class.java) {
						name = "Job-Token"
						value = token
					}

					authentication {
						create("header", HttpHeaderAuthentication::class.java)
					}
				}
			}
		}

		// endregion
		// region Maven Central

		target.extensions.configure<PublishingExtension> {
			publications.withType(MavenPublication::class.java) {
				artifact(documentationJar)

				pom {
					setPomMetadataForMavenCentral(config, this, target)
				}
			}
		}

		target.afterEvaluate {
			target.tasks.withType(GenerateMavenPom::class.java) {
				if (name.matches(Regex("generatePomFileFor.*MarkerMavenPublication"))) {
					setPomMetadataForMavenCentral(config, pom, target)
				}
			}
		}

		// endregion
		// region Signing

		run {
			val keyId = System.getenv("SIGNING_KEY_ID") ?: return@run
			val password = System.getenv("SIGNING_PASSWORD") ?: return@run
			val keyRing = System.getenv("SIGNING_KEY_RING") ?: return@run

			target.extensions.extraProperties.set("signing.keyId", keyId)
			target.extensions.extraProperties.set("signing.password", password)
			target.extensions.extraProperties.set("signing.secretKeyRingFile", keyRing)

			// Workaround for https://youtrack.jetbrains.com/issue/KT-61858
			val signingTasks = target.tasks.withType(Sign::class.java)
			target.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
				dependsOn(signingTasks)
			}

			target.extensions.configure<SigningExtension> {
				sign(target.extensions.getByType<PublishingExtension>().publications)
			}
		}

		// endregion
		// region Code coverage

		target.extensions.configure<KoverProjectExtension> {
			reports {
				filters {
					excludes {
						packages("gradle.kotlin.dsl.accessors", "gradle.kotlin.dsl.plugins")
					}
				}

				verify {
					rule {
						disabled.set(false)
						groupBy.set(GroupingEntityType.APPLICATION)

						bound {
							coverageUnits.set(CoverageUnit.BRANCH)
							minValue.set(config.coverage)
						}
					}
				}
			}
		}

		// endregion
	}

	private fun configureDokkaSourceSet(target: Project, spec: DokkaSourceSetSpec) {
		if (spec.name.endsWith("Main") || spec.name == "main") {
			val setName = spec.name.removeSuffix("Main")

			val headerName = if (setName == "common" || spec.name == "main") "README.md"
			else "README.$setName.md"

			val headerPath = "${target.projectDir}/$headerName"
			if (java.io.File(headerPath).exists())
				spec.includes.from(headerPath)
			else
				target.logger.info("No specific documentation file found for $setName, expected to find $headerPath")
		}

		spec.externalDocumentationLinks.register("KotlinX.Coroutines") {
			url.set(URI("https://kotlinlang.org/api/kotlinx.coroutines/"))
		}
		spec.externalDocumentationLinks.register("KotlinX.Serialization") {
			url.set(URI("https://kotlinlang.org/api/kotlinx.serialization/"))
		}
		spec.externalDocumentationLinks.register("Ktor") {
			url.set(URI("https://api.ktor.io/"))
		}
		spec.externalDocumentationLinks.register("Arrow") {
			url.set(URI("https://apidocs.arrow-kt.io"))
		}

		val projectUrl = System.getenv("CI_PROJECT_URL")
		val commit = System.getenv("CI_COMMIT_SHA") ?: "main"

		if (projectUrl != null) {
			spec.sourceLink {
				val path = target.projectDir.relativeTo(target.rootProject.projectDir)

				localDirectory.set(target.file("src"))
				remoteUrl.set(URI("$projectUrl/-/blob/$commit/$path/src"))
				remoteLineSuffix.set("#L")
			}
		}
	}

	private fun setPomMetadataForMavenCentral(config: LibraryExtension, pom: MavenPom, target: Project) {
		pom.name.set(config.name)
		pom.description.set(config.description)
		pom.url.set(config.homeUrl)

		pom.licenses {
			target.afterEvaluate {
				license(config.license.get())
			}
		}

		pom.developers {
			developer {
				id.set("opensavvy")
				name.set("OpenSavvy")
				email.set("contact@opensavvy.dev")
			}
		}

		pom.issueManagement {
			system.set("GitLab")
			url.set(System.getenv("CI_PROJECT_URL") + "/-/issues/new")
		}

		pom.scm {
			url.set(System.getenv("CI_PROJECT_URL"))
			connection.set("scm:git:" + System.getenv("CI_REPOSITORY_URL"))
			tag.set(System.getenv("CI_COMMIT_REF_NAME"))
		}
	}
}
