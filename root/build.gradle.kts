import dev.opensavvy.conventions.versions.Versions

buildscript {
	dependencies {
		classpath("dev.opensavvy.gradle.conventions:versions:$version")
	}
}

plugins {
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

dependencies {
	// Not actually used, but needs to be part of the classpath of the root plugin to force
	// this exact specific version.
	// Required by the Kotlin and the Dokkatoo plugins.
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")

	api(libs.gradle.nexusPublish)
	api(libs.gradle.dokkatoo)
	api(libs.gradle.kover)
}
