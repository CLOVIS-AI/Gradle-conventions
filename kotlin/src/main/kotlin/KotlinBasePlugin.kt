package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.process.CommandLineArgumentProvider
import org.jetbrains.kotlin.gradle.ExperimentalJsTestDsl
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrTarget
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension

class KotlinBasePlugin : Plugin<Project> {

	@OptIn(ExperimentalKotlinGradlePluginApi::class)
	override fun apply(target: Project) {
		target.pluginManager.apply("org.jetbrains.kotlin.multiplatform")
		target.pluginManager.apply("org.jetbrains.kotlin.plugin.power-assert")

		/**
		 * Automatically adds the KotlinX.Coroutines Debug Agent to JVM tests.
		 *
		 * Disable with -PcoroutinesDebugAgent=false
		 */
		val coroutinesDebugAgentEnabled = (target.findProperty("coroutinesDebugAgent") as? String)?.toBoolean() ?: true
		val coroutinesDebugAgentJar by lazy {
			target.configurations.detachedConfiguration(
				target.dependencies.create("org.jetbrains.kotlinx:kotlinx-coroutines-debug:${Versions.KOTLINX_COROUTINES}")
			).apply {
				isTransitive = false
			}.singleFile
		}

		target.tasks.withType(Test::class.java) {
			useJUnitPlatform()
			testLogging {
				showExceptions = false
			}

			if (coroutinesDebugAgentEnabled) {
				jvmArgumentProviders.add(object : CommandLineArgumentProvider {
					@get:Classpath
					val agentClasspath = target.files(coroutinesDebugAgentJar)

					override fun asArguments() = listOf("-javaagent:${coroutinesDebugAgentJar.absolutePath}")
				})

				systemProperty("kotlinx.coroutines.debug.enable.creation.stack.trace", "true")
			}
		}

		target.extensions.configure<PowerAssertGradleExtension> {
			functions.set(
				listOf(
					"kotlin.assert",
					"kotlin.require",
					"kotlin.requireNotNull",
					"kotlin.check",
					"kotlin.checkNotNull",
					"kotlin.test.assertTrue",
					"kotlin.test.assertFalse",
					"kotlin.test.assertEquals",
					"kotlin.test.assertNull",
					"opensavvy.prepared.suite.assertions.log",
				)
			)
		}

		target.extensions.configure<KotlinMultiplatformExtension> {
			compilerOptions {
				freeCompilerArgs.add("-Xreturn-value-checker=full")
			}

			/**
			 * Configures which browsers to use for JS tests.
			 *
			 * Configure with -Popensavvy.js.browser=<browser> or in gradle.properties:
			 * - `default` (or unset): chromium and firefox
			 * - `chromium`: only chromium
			 * - `firefox`: only firefox
			 * - `webkit`: only webkit
			 */
			val jsBrowser = (target.findProperty("opensavvy.js.browser") as? String)?.trim()?.lowercase() ?: "default"

			targets.configureEach {
				if (this is KotlinJsIrTarget && platformType == KotlinPlatformType.js) {
					subTargets.configureEach {
						if (this is KotlinJsBrowserDsl) {
							@OptIn(ExperimentalJsTestDsl::class)
							test {
								when (jsBrowser) {
									"default" -> {
										chromium()
										firefox()
									}

									"chromium" -> chromium()
									"firefox" -> firefox()
									"webkit" -> webkit()
									else -> error("Unknown browser '$jsBrowser' configured in 'opensavvy.js.browser'. Expected one of: 'default', 'chromium', 'firefox', 'webkit'.")
								}
							}
						}
					}
				}
			}
		}
	}
}
