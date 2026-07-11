package dev.opensavvy.conventions

import org.gradle.api.HasImplicitReceiver
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.testing.Test
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.jetbrains.kotlin.samWithReceiver.gradle.SamWithReceiverExtension
import tapmoc.TapmocExtension
import java.net.URI

class OpenSavvyPluginPlugin : Plugin<Project> {

	// The Kotlin version displayed by './gradlew --version'
	private val targetedKotlin = "2.3.0"

	// https://gitlab.com/opensavvy/groundwork/prepared/-/releases
	private val prepared = "2.5.3"

	private val javaCompatibility = 17

	override fun apply(target: Project) {
		target.pluginManager.apply("maven-publish")
		target.pluginManager.apply("org.jetbrains.kotlin.jvm")
		target.pluginManager.apply("org.jetbrains.kotlin.plugin.sam.with.receiver")
		target.pluginManager.apply("java-gradle-plugin")
		target.pluginManager.apply("com.gradleup.tapmoc")

		val samWithReceiver = target.extensions.getByType(SamWithReceiverExtension::class.java)
		samWithReceiver.annotation(HasImplicitReceiver::class.qualifiedName!!)

		val java = target.extensions.getByType(JavaPluginExtension::class.java)
		java.withSourcesJar()

		val tapmoc = target.extensions.getByType(TapmocExtension::class.java)
		tapmoc.java(javaCompatibility)
		tapmoc.kotlin(targetedKotlin)

		/*
		 * When running in GitLab, uses the auto-created CI variables to configure the GitLab Maven Registry.
		 * For more information on the variables and their values, see:
		 * - https://docs.gitlab.com/ee/user/packages/maven_repository/
		 * - https://docs.gitlab.com/ee/ci/variables/predefined_variables.html
		 */
		target.extensions.getByType(PublishingExtension::class.java).apply {
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
						register("header", HttpHeaderAuthentication::class.java)
					}
				}
			}

			publications.withType(MavenPublication::class.java) {
				if (name.matches(Regex(".*PluginMarkerMaven"))) {
					artifact(target.tasks.getByName("sourcesJar"))
				}
			}
		}

		target.tasks.withType(Test::class.java).configureEach {
			useJUnitPlatform()
		}

		target.dependencies.add("testImplementation", "dev.opensavvy.prepared:suite:$prepared")
		target.dependencies.add("testImplementation", "dev.opensavvy.prepared:runner-kotlin-test:$prepared")
		target.dependencies.add("testImplementation", "dev.opensavvy.prepared:compat-gradle:$prepared")
	}
}
