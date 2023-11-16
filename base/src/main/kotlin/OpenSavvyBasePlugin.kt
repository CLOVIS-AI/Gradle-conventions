package dev.opensavvy.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate

@Suppress("unused")
class OpenSavvyBasePlugin : Plugin<Project> {
	override fun apply(target: Project) {
		val appVersion: String? by target
		val appGroup: String? by target

		target.version = appVersion ?: "DEV"

		appGroup?.let {
			target.group = it
		} ?: target.logger.warn("Missing group declaration; you should add 'appGroup=<your group name>' in the gradle.properties file")
	}
}
