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
				version("composeMultiplatform", Versions.COMPOSE_MULTIPLATFORM)
				version("java-compat", Versions.JAVA_COMPAT.toString())
				version("java-app", Versions.JAVA_APP.toString())
				version("kotest", Versions.KOTEST)
				version("kotest-plugin", Versions.KOTEST_PLUGIN)
				version("self", OPENSAVVY_CONVENTIONS_VERSION)

				// Our other convention plugins
				plugin("base", "dev.opensavvy.conventions.base").versionRef("self")
				plugin("root", "dev.opensavvy.conventions.root").versionRef("self")
				plugin("plugin", "dev.opensavvy.conventions.plugin").versionRef("self")
				plugin("kotlin-base", "dev.opensavvy.conventions.kotlin.base").versionRef("self")
				plugin("kotlin-library", "dev.opensavvy.conventions.kotlin.library").versionRef("self")
				plugin("kotlin-abstractLibrary", "dev.opensavvy.conventions.kotlin.abstractLibrary").versionRef("self")
				plugin("kotlin-application", "dev.opensavvy.conventions.kotlin.application").versionRef("self")
				plugin("kotlin-internal", "dev.opensavvy.conventions.kotlin.internal").versionRef("self")

				// Plugins and libraries that must use the same versions as ours
				plugin("aligned-kotlin", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
				plugin("aligned-kotlinx-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
				plugin("aligned-composeMultiplatform", "org.jetbrains.compose").versionRef("composeMultiplatform")
				plugin("aligned-composeCompiler", "org.jetbrains.kotlin.plugin.compose").versionRef("kotlin")
				plugin("aligned-kotest", "io.kotest.multiplatform").versionRef("kotest-plugin")
				library("aligned-kotlin-test", "org.jetbrains.kotlin", "kotlin-test").versionRef("kotlin")
				library("aligned-kotlin-test-common", "org.jetbrains.kotlin", "kotlin-test-common").versionRef("kotlin")
				library("aligned-kotlin-test-annotations", "org.jetbrains.kotlin", "kotlin-test-annotations-common").versionRef("kotlin")
				library("aligned-kotlin-test-junit", "org.jetbrains.kotlin", "kotlin-test-junit").versionRef("kotlin")
				library("aligned-kotlin-test-junit5", "org.jetbrains.kotlin", "kotlin-test-junit5").versionRef("kotlin")
				library("aligned-kotlin-test-js", "org.jetbrains.kotlin", "kotlin-test-js").versionRef("kotlin")
			}
		}
	}
}
