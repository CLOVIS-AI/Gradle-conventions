package dev.opensavvy.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.testing.Test
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.kotlin.dsl.*
import tapmoc.TapmocExtension
import java.net.URI

class OpenSavvyPluginPlugin : Plugin<Project> {

	// https://gitlab.com/opensavvy/groundwork/prepared/-/releases
	private val prepared = "2.0.6"

	private val javaCompatibility = 17
	private val javaForTesting = 21

	override fun apply(target: Project) {
		target.pluginManager.apply("maven-publish")
		target.pluginManager.apply("org.gradle.kotlin.kotlin-dsl")
		target.pluginManager.apply("com.gradleup.tapmoc")

		target.extensions.configure<JavaPluginExtension> {
			withSourcesJar()
		}

		val tapmoc = target.extensions.getByType(TapmocExtension::class.java)
		tapmoc.java(javaCompatibility)

		/*
		 * When running in GitLab, uses the auto-created CI variables to configure the GitLab Maven Registry.
		 * For more information on the variables and their values, see:
		 * - https://docs.gitlab.com/ee/user/packages/maven_repository/
		 * - https://docs.gitlab.com/ee/ci/variables/predefined_variables.html
		 */
		target.extensions.configure<PublishingExtension> {
			repositories {
				val projectId = System.getenv("CI_PROJECT_ID") ?: return@repositories
				val token = System.getenv("CI_JOB_TOKEN") ?: return@repositories
				val api = System.getenv("CI_API_V4_URL") ?: return@repositories

				maven {
					name = "gitlab"
					url = URI("$api/projects/$projectId/packages/maven")

					credentials(HttpHeaderCredentials::class.java) {
						name = "Job-Token"
						value = token
					}

					authentication {
						register<HttpHeaderAuthentication>("header")
					}
				}
			}

			publications.withType(MavenPublication::class.java) {
				if (name.matches(Regex(".*PluginMarkerMaven"))) {
					artifact(target.tasks["sourcesJar"])
				}
			}
		}

		val version = target.provider {
			val version = embeddedKotlinVersion.replaceAfterLast('.', "") // Remove the patch version
				.removeSuffix(".")
			target.logger.info("Setting the Kotlin compatibility target to $version")
			@Suppress("EnumValuesSoftDeprecate") // '.entries' does not exist yet for the Kotlin version used in plugins
			org.jetbrains.kotlin.gradle.dsl.KotlinVersion.values().find { it.version == version }
				?: error("Could not set Kotlin compatibility for this plugin module to $version. Embedded Kotlin version: $embeddedKotlinVersion. Known versions: ${org.jetbrains.kotlin.gradle.dsl.KotlinVersion.values().map { it.version }}")
		}

		tapmoc.kotlin("${version.get().version}.0")

		target.tasks.withType(Test::class.java) {
			useJUnitPlatform()
			javaLauncher = target.extensions.getByType<JavaToolchainService>().launcherFor {
				languageVersion.set(JavaLanguageVersion.of(javaForTesting))
			}
		}

		val testImplementation by target.configurations.getting
		target.dependencies {
			testImplementation("dev.opensavvy.prepared:suite:$prepared")
			testImplementation("dev.opensavvy.prepared:runner-kotest:$prepared")
			testImplementation("dev.opensavvy.prepared:runner-kotlin-test:$prepared")
			testImplementation("dev.opensavvy.prepared:compat-gradle:$prepared")
		}
	}
}
