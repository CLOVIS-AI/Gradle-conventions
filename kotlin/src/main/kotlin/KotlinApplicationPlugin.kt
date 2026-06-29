package dev.opensavvy.conventions.kotlin

import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinApplicationPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.pluginManager.apply("dev.opensavvy.conventions.kotlin.base")
		target.pluginManager.apply("dev.opensavvy.conventions.kotlin.internal")
	}
}
