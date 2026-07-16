package dev.opensavvy.conventions

import opensavvy.prepared.compat.gradle.gradle
import opensavvy.prepared.compat.gradle.settingsKts
import opensavvy.prepared.runner.testballoon.preparedSuite
import opensavvy.prepared.suite.config.CoroutineTimeout
import kotlin.time.Duration.Companion.minutes

val BasePluginsTest by preparedSuite {
	test("Initialize Gradle", CoroutineTimeout(10.minutes)) {
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
