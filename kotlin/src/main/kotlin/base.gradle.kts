package dev.opensavvy.conventions.kotlin

plugins {
	kotlin("multiplatform")
}

repositories {
	mavenCentral()
	google()
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
