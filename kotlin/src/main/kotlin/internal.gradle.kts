package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions

plugins {
	id("dev.opensavvy.conventions.kotlin.base")
	id("com.gradleup.tapmoc")
}

tapmoc {
	java(Versions.JAVA_APP)
}