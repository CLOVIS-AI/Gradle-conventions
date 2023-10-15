package dev.opensavvy.conventions.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.caching.http.HttpBuildCache
import org.gradle.kotlin.dsl.provideDelegate
import java.net.URI

class BuildCache : Plugin<Settings> {

	override fun apply(target: Settings) {
		val buildCacheUrl: String? by target

		val username = System.getenv("GRADLE_BUILD_CACHE_CREDENTIALS")?.split(':')?.get(0)
		val password = System.getenv("GRADLE_BUILD_CACHE_CREDENTIALS")?.split(':')?.get(1)

		val mainBranch: String? = System.getenv("CI_DEFAULT_BRANCH")
		val currentBranch: String? = System.getenv("CI_COMMIT_REF_NAME")

		target.buildCache {
			remote(HttpBuildCache::class.java) {
				url = URI(buildCacheUrl ?: "https://gradle.opensavvy.dev/cache/")

				if (username != null && password != null) credentials {
					this.username = username
					this.password = password
				}

				isPush = mainBranch != null && currentBranch != null && mainBranch == currentBranch
			}
		}
	}
}
