package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
	id("dev.opensavvy.conventions.kotlin.base")
	id("dev.opensavvy.conventions.kotlin.abstractLibrary")
	id("org.jetbrains.kotlin.plugin.power-assert")
}

kotlin {
	jvmToolchain(Versions.JAVA_COMPAT)
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
