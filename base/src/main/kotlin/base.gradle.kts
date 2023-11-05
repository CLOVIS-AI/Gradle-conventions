package dev.opensavvy.conventions

plugins {
	base
}

val appVersion: String? by project
val appGroup: String? by project

version = appVersion ?: "DEV"
group = appGroup ?: error("Missing group declaration; you should add 'appGroup=<your group name>' in the gradle.properties file")
