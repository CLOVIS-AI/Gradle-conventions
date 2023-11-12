import dev.opensavvy.conventions.versions.Versions

buildscript {
	dependencies {
		classpath("dev.opensavvy.gradle.conventions:versions:$version")
	}
}

plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	// Not actually used, but needs to be part of the classpath of the root plugin to force
	// this exact specific version.
	// Required by the Kotlin and the Dokkatoo plugins.
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")

	api(libs.gradle.nexusPublish)
	api(libs.gradle.dokkatoo)

	testImplementation(project(":test-utils"))
}

repositories {
	gradlePluginPortal()
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
