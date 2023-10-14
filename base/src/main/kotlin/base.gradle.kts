package dev.opensavvy.conventions

import org.gradle.kotlin.dsl.base
import org.gradle.kotlin.dsl.provideDelegate

plugins {
	base
}

val appVersion: String? by project
val appGroup: String? by project

version = appVersion ?: "0.0.0-dev"
group = appGroup ?: error("Missing group declaration; you should add 'appGroup=<your group name>' in the gradle.properties file")
