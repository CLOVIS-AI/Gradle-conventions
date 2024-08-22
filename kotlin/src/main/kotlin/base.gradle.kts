package dev.opensavvy.conventions.kotlin

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
	kotlin("multiplatform")
	id("org.jetbrains.kotlin.plugin.power-assert")
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
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
