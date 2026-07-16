package dev.opensavvy.conventions.kotlin

import dev.opensavvy.conventions.versions.Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.process.CommandLineArgumentProvider
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension

class KotlinBasePlugin : Plugin<Project> {

	@OptIn(ExperimentalKotlinGradlePluginApi::class)
	override fun apply(target: Project) {
		target.pluginManager.apply("org.jetbrains.kotlin.multiplatform")
		target.pluginManager.apply("org.jetbrains.kotlin.plugin.power-assert")

		val coroutinesDebugAgentJar = target.configurations.detachedConfiguration(
			target.dependencies.create("org.jetbrains.kotlinx:kotlinx-coroutines-debug:${Versions.KOTLINX_COROUTINES}")
		).apply {
			isTransitive = false
		}.singleFile

		target.tasks.withType(Test::class.java) {
			useJUnitPlatform()
			testLogging {
				showExceptions = false
			}

			jvmArgumentProviders.add(object : CommandLineArgumentProvider {
				@get:Classpath
				val agentClasspath = target.files(coroutinesDebugAgentJar)

				override fun asArguments() = listOf("-javaagent:${coroutinesDebugAgentJar.absolutePath}")
			})

			systemProperty("kotlinx.coroutines.debug.enable.creation.stack.trace", "true")
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
		}
	}
}
