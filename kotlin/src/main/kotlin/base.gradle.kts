package dev.opensavvy.conventions.kotlin

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
	kotlin("multiplatform")
	id("org.jetbrains.kotlin.plugin.power-assert")
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()

	testLogging {
		showExceptions = true
		showStackTraces = true
		showCauses = true
		exceptionFormat = TestExceptionFormat.FULL
	}
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
	functions = listOf(
		// Standard library
		"kotlin.assert",
		"kotlin.require",
		"kotlin.requireNotNull",
		"kotlin.check",
		"kotlin.checkNotNull",

		// Standard test library
		"kotlin.test.assertTrue",
		"kotlin.test.assertFalse",
		"kotlin.test.assertEquals",
		"kotlin.test.assertNull",
	)
}
