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
	api("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
	implementation("dev.opensavvy.gradle.conventions:versions:$version")

	testImplementation(project(":test-utils"))
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
