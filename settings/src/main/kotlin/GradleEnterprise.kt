package dev.opensavvy.conventions.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.develocity

class GradleEnterprise : Plugin<Settings> {

	override fun apply(target: Settings) {
		val gradleEnterpriseEnabled = System.getenv("OPENSAVVY_GRADLE_ENTERPRISE").toBoolean()
		val runningInCi = System.getenv("CI").toBoolean()

		if (gradleEnterpriseEnabled) {
			target.plugins.apply("com.gradle.develocity")

			target.develocity {
				buildScan {
					termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
					termsOfUseAgree.set("yes")

					if (runningInCi) {
						publishing.onlyIf { true }
						uploadInBackground.set(false)

						// Metadata to help understand what happened
						tag("CI")
						value("Runner", System.getenv("CI_RUNNER_DESCRIPTION"))
						link("Job details", System.getenv("CI_JOB_URL"))
						link("Pipeline details", System.getenv("CI_PIPELINE_URL"))
						link("Project", System.getenv("CI_PROJECT_URL"))
					}
				}
			}
		} else if (runningInCi) {
			println("To generate a Gradle Build Scan if this execution fails, create the environment variable 'OPENSAVVY_GRADLE_ENTERPRISE=true'. This implies accepting the Gradle terms of service. Learn more in the OpenSavvy Gradle Conventions.")
		}
	}
}
