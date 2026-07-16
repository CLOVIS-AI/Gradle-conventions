package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import tapmoc.TapmocExtension

class KotlinLibraryPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.pluginManager.apply("dev.opensavvy.conventions.kotlin.base")
		target.pluginManager.apply("dev.opensavvy.conventions.kotlin.abstractLibrary")
		target.pluginManager.apply("com.gradleup.tapmoc")

		target.extensions.getByType<TapmocExtension>().java(Versions.JAVA_COMPAT)

		target.tasks.withType(KotlinCompile::class.java) {
			if (!name.contains("test", ignoreCase = true)) {
				compilerOptions {
					freeCompilerArgs.add("-XXexplicit-return-types=warning")
				}
			}
		}
	}
}
