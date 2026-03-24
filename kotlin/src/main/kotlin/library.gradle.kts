package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("dev.opensavvy.conventions.kotlin.base")
	id("dev.opensavvy.conventions.kotlin.abstractLibrary")
	id("com.gradleup.tapmoc")
}

tapmoc {
	java(Versions.JAVA_COMPAT)
}

// region Explicit return types

tasks.withType<KotlinCompile> {
	if (!this.name.contains("test", ignoreCase = true)) {
		compilerOptions {
			freeCompilerArgs.add("-XXexplicit-return-types=warning")
		}
	}
}

// endregion
