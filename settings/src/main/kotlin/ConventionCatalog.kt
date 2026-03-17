package dev.opensavvy.conventions.settings

import dev.opensavvy.conventions.versions.*
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class ConventionCatalog : Plugin<Settings> {

	override fun apply(target: Settings) {
		target.dependencyResolutionManagement.versionCatalogs {
			register("opensavvyConventions") {
				// Aligned versions used by all our projects
				version("java-compat", Versions.JAVA_COMPAT.toString())
				version("java-app", Versions.JAVA_APP.toString())
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

				// Other plugins we use
				plugin("kover", "org.jetbrains.kotlinx:kover-gradle-plugin").version(OPENSAVVY_CONVENTIONS_VERSION_KOVER)
				plugin("nmcp", "com.gradleup.nmcp:nmcp").version(OPENSAVVY_CONVENTIONS_VERSION_NMCP)
				plugin("tapmoc", "com.gradleup.tapmoc:com.gradleup.tapmoc.gradle.plugin").version(OPENSAVVY_CONVENTIONS_VERSION_TAPMOC)
			}
		}
	}
}
