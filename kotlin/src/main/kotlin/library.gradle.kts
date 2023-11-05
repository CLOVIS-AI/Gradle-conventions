package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions

plugins {
	id("dev.opensavvy.conventions.kotlin.base")
}

kotlin {
	jvmToolchain(Versions.JAVA_COMPAT)
}
