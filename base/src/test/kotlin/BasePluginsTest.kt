package dev.opensavvy.conventions

import opensavvy.prepared.compat.gradle.gradle
import opensavvy.prepared.compat.gradle.settingsKts
import opensavvy.prepared.runner.kotlin.TestExecutor
import opensavvy.prepared.suite.SuiteDsl
import opensavvy.prepared.suite.config.CoroutineTimeout
import kotlin.time.Duration.Companion.minutes

class BasePluginsTest : TestExecutor() {
	override fun SuiteDsl.register() {
		test("Initialize Gradle", config = CoroutineTimeout(10.minutes)) {
			// The very first test that runs needs to install Gradle etc,
			// without this, we would need to increase the timeout for all other tests.

			gradle.settingsKts("""
				rootProject.name = "test"
			""".trimIndent())

			gradle.runner()
				.withArguments("projects")
				.build()
		}

		baseTests()
	}
}
