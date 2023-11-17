package dev.opensavvy.conventions.settings

import dev.opensavvy.conventions.versions.OPENSAVVY_CONVENTIONS_VERSION
import dev.opensavvy.conventions.versions.Versions
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class ConventionCatalog : Plugin<Settings> {

	override fun apply(target: Settings) {
		target.dependencyResolutionManagement.versionCatalogs {
			register("opensavvyConventions") {
				// Aligned versions used by all our projects
				version("kotlin", Versions.KOTLIN)
				version("java-compat", Versions.JAVA_COMPAT.toString())
				version("java-app", Versions.JAVA_APP.toString())
				version("self", OPENSAVVY_CONVENTIONS_VERSION)

				// Our other convention plugins
				plugin("base", "dev.opensavvy.conventions.base").versionRef("self")
				plugin("root", "dev.opensavvy.conventions.root").versionRef("self")
				plugin("plugin", "dev.opensavvy.conventions.plugin").versionRef("self")
				plugin("kotlin-base", "dev.opensavvy.conventions.kotlin.base").versionRef("self")
				plugin("kotlin-library", "dev.opensavvy.conventions.kotlin.library").versionRef("self")
				plugin("kotlin-application", "dev.opensavvy.conventions.kotlin.application").versionRef("self")

				// Plugins and libraries that must use the same versions as ours
				plugin("aligned-kotlin", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
				plugin("aligned-kotlinx-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
				library("aligned-kotlin-test-common", "org.jetbrains.kotlin", "kotlin-test-common").versionRef("kotlin")
				library("aligned-kotlin-test-annotations", "org.jetbrains.kotlin", "kotlin-test-annotations-common").versionRef("kotlin")
				library("aligned-kotlin-test-junit", "org.jetbrains.kotlin", "kotlin-test-junit").versionRef("kotlin")
				library("aligned-kotlin-test-junit5", "org.jetbrains.kotlin", "kotlin-test-junit5").versionRef("kotlin")
				library("aligned-kotlin-test-js", "org.jetbrains.kotlin", "kotlin-test-js").versionRef("kotlin")
			}
		}
	}
}
