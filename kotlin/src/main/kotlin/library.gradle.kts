package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions
import gradle.kotlin.dsl.accessors._55d9ef7b350efd189ea67d48808406de.dokkatoo
import gradle.kotlin.dsl.accessors._55d9ef7b350efd189ea67d48808406de.publishing
import gradle.kotlin.dsl.accessors._55d9ef7b350efd189ea67d48808406de.signing
import java.net.URI

plugins {
	id("dev.opensavvy.conventions.kotlin.base")

	id("maven-publish")
	id("signing")
	id("dev.adamko.dokkatoo-html")
}

kotlin {
	jvmToolchain(Versions.JAVA_COMPAT)
}

interface LibraryExtension {
	val name: Property<String>
	val description: Property<String>
	val homeUrl: Property<String>
	val license: Property<Action<MavenPomLicense>>
}

val config = extensions.create<LibraryExtension>("library")

// region GitLab Maven Registry

// When running in GitLab CI, uses the auto-created CI variables to configure the GitLab Maven Registry.
// For more information on the variables and their values, see:
// - https://docs.gitlab.com/ee/user/packages/maven_repository/
// - https://docs.gitlab.com/ee/ci/variables/predefined_variables.html
publishing {
	repositories {
		val projectId = System.getenv("CI_PROJECT_ID") ?: return@repositories
		val token = System.getenv("CI_JOB_TOKEN") ?: return@repositories
		val api = System.getenv("CI_API_V4_URL") ?: return@repositories

		maven {
			name = "GitLab"
			url = uri("$api/projects/$projectId/packages/maven")

			credentials(HttpHeaderCredentials::class.java) {
				name = "Job-Token"
				value = token
			}

			authentication {
				create<HttpHeaderAuthentication>("header")
			}
		}
	}
}

// endregion
// region Maven Central

val fakeJavadocJar by tasks.registering(Jar::class) {
	description = "Fake documentation JAR for MavenCentral"
	group = "publishing"

	archiveClassifier.set("javadoc")
}

publishing {
	publications.withType<MavenPublication> {
		artifact(fakeJavadocJar.get())

		pom {
			name.set(config.name)
			description.set(config.description)
			url.set(config.homeUrl)

			licenses {
				afterEvaluate {
					license(config.license.get())
				}
			}

			developers {
				developer {
					id.set("opensavvy")
					name.set("OpenSavvy")
					email.set("contact@opensavvy.dev")
				}
			}

			scm {
				url.set(System.getenv("CI_PROJECT_URL"))
			}
		}
	}
}

run {
	ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID") ?: return@run
	ext["signing.password"] = System.getenv("SIGNING_PASSWORD") ?: return@run
	ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_KEY_RING") ?: return@run

	// Workaround for https://youtrack.jetbrains.com/issue/KT-61858
	val signingTasks = tasks.withType(Sign::class)
	tasks.withType(AbstractPublishToMaven::class).configureEach {
		dependsOn(signingTasks)
	}

	signing {
		sign(publishing.publications)
	}
}

// endregion
// region Documentation

dokkatoo {
	moduleName.set(config.name)

	dokkatooSourceSets.configureEach {
		// region Include the correct HTML file, if it exists
		if (name.endsWith("Main") || name == "main") {
			val setName = name.removeSuffix("Main")

			val headerName =
				if (setName == "common" || name == "main") "README.md"
				else "README.$setName.md"

			val headerPath = "${project.projectDir}/$headerName"
			if (File(headerPath).exists())
				includes.from(headerPath)
			else
				logger.info("No specific documentation file found for $setName, expected to find $headerPath")
		}
		// endregion
		// region Dependencies

		fun dependencyDocumentation(name: String, url: String) = externalDocumentationLinks.register(name) {
			this.url.set(URI(url))
		}

		dependencyDocumentation("KotlinX.Coroutines", "https://kotlinlang.org/api/kotlinx.coroutines/")
		dependencyDocumentation("KotlinX.Serialization", "https://kotlinlang.org/api/kotlinx.serialization/")
		dependencyDocumentation("Ktor", "https://api.ktor.io/")
		dependencyDocumentation("Arrow", "https://apidocs.arrow-kt.io")

		// endregion
		// region Link to the sources

		val projectUrl = System.getenv("CI_PROJECT_URL")
		val commit = System.getenv("CI_COMMIT_SHA") ?: "main"

		if (projectUrl != null) {
			sourceLink {
				val path = projectDir.relativeTo(rootProject.projectDir)

				localDirectory.set(file("src"))
				remoteUrl.set(URI("$projectUrl/-/blob/$commit/$path/src"))
				remoteLineSuffix.set("#L")
			}
		}

		// endregion
	}
}

// endregion