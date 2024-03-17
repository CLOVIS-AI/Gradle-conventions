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
	api("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
	implementation("dev.opensavvy.gradle.conventions:versions:$version")

	implementation(libs.gradle.dokkatoo)
	implementation(libs.gradle.nexusPublish)
	implementation(libs.gradle.kover)
}
