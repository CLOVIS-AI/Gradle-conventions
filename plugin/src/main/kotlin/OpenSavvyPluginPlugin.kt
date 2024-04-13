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
import java.net.URI

class OpenSavvyPluginPlugin : Plugin<Project> {

	// https://gitlab.com/opensavvy/groundwork/prepared/-/releases
	private val prepared = "1.0.0"

	private val javaCompatibility = 11 // for Dokkatoo
	private val javaForTesting = 17

	override fun apply(target: Project) {
		target.pluginManager.apply("maven-publish")
		target.pluginManager.apply("org.gradle.kotlin.kotlin-dsl")

		target.extensions.configure<JavaPluginExtension> {
			withSourcesJar()

			toolchain {
				languageVersion.set(JavaLanguageVersion.of(javaCompatibility))
			}
		}

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
			testImplementation("dev.opensavvy.prepared:compat-gradle:$prepared")
		}
	}
}
