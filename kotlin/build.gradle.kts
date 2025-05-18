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
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
	implementation("org.jetbrains.kotlin:kotlin-power-assert:${libs.versions.kotlin.get()}")
	implementation("dev.opensavvy.gradle.conventions:versions:$version")

	implementation(libs.gradle.dokka)
	implementation(libs.gradle.dokka.mkdocs)
	implementation(libs.gradle.nexusPublish)
	implementation(libs.gradle.kover)
}
