package dev.opensavvy.conventions.kotlin

plugins {
	kotlin("multiplatform")
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
