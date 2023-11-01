package dev.opensavvy.conventions.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class Combined : Plugin<Settings> {

	override fun apply(target: Settings) {
		target.plugins.apply(BuildCache::class.java)
		target.plugins.apply(ConventionCatalog::class.java)
	}
}
