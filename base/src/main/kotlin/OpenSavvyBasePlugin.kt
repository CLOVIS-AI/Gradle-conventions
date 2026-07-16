package dev.opensavvy.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class OpenSavvyBasePlugin : Plugin<Project> {
	override fun apply(target: Project) {
		val appVersion: String? = target.findProperty("appVersion") as? String?
		val appGroup: String? = target.findProperty("appGroup") as? String?

		target.version = appVersion ?: "DEV"

		if (appGroup != null) {
			target.group = appGroup
		} else {
			target.logger.warn("Missing group declaration; you should add 'appGroup=<your group name>' in the gradle.properties file")
		}
	}
}
