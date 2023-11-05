package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions
import org.gradle.kotlin.dsl.application

plugins {
	id("dev.opensavvy.conventions.kotlin.base")
	application
}

kotlin {
	jvmToolchain(Versions.JAVA_APP)
}
