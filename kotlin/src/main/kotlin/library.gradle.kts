package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions

plugins {
	id("dev.opensavvy.conventions.kotlin.base")
	id("dev.opensavvy.conventions.kotlin.abstractLibrary")
	id("com.gradleup.tapmoc")
}

tapmoc {
	java(Versions.JAVA_COMPAT)
}

// region Explicit return types

kotlin {
	sourceSets.configureEach {
		if (name.endsWith("Main", ignoreCase = true)) {
			compilerOptions {
				freeCompilerArgs = listOf("-XXexplicit-return-types=strict")
			}
		}
	}
}

// endregion
