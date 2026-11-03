package dev.opensavvy.conventions.kotlin

import com.gradleup.tapmoc.TapmocExtension
import dev.opensavvy.conventions.versions.Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class KotlinInternalPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.pluginManager.apply("dev.opensavvy.conventions.kotlin.base")
		target.pluginManager.apply("com.gradleup.tapmoc")

		target.extensions.getByType<TapmocExtension>().java(Versions.JAVA_APP)
	}
}
