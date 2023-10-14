package dev.opensavvy.conventions

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class BaseTest {

	@TempDir
	lateinit var testProjectDir: File

	val settingsFile get() = File(testProjectDir, "settings.gradle.kts")
	val buildFile get() = File(testProjectDir, "build.gradle.kts")
	val properties get() = File(testProjectDir, "gradle.properties")

	@Test
	fun failsIfMissingGroup() {
		settingsFile.writeText("""
			rootProject.name = "hello-world"
		""".trimIndent())

		buildFile.writeText("""
			plugins {
			    id("dev.opensavvy.conventions.base")
			}
		""".trimIndent())

		val result = GradleRunner.create()
			.withProjectDir(testProjectDir)
			.withPluginClasspath()
			.withArguments("build")
			.buildAndFail()

		assertTrue(result.output.contains("Missing group declaration; you should add 'appGroup=<your group name>' in the gradle.properties file"), "Output: ${result.output}")
	}

	@Test
	fun group() {
		settingsFile.writeText("""
			rootProject.name = "hello-world"
		""".trimIndent())

		buildFile.writeText("""
			plugins {
			    id("dev.opensavvy.conventions.base")
			}
			
			tasks.register("printGroup") {
				doLast {
					println("Group: " + project.group)
				}
			}
		""".trimIndent())

		properties.writeText("""
			appGroup = dev.opensavvy.test
		""".trimIndent())

		val result = GradleRunner.create()
			.withProjectDir(testProjectDir)
			.withPluginClasspath()
			.withArguments("printGroup")
			.build()

		assertTrue(result.output.contains("Group: dev.opensavvy.test"), "Output: ${result.output}")
	}

	@Test
	fun versionSpecified() {
		settingsFile.writeText("""
			rootProject.name = "hello-world"
		""".trimIndent())

		buildFile.writeText("""
			plugins {
			    id("dev.opensavvy.conventions.base")
			}
			
			tasks.register("printVersion") {
				doLast {
					println("Version: " + project.version)
				}
			}
		""".trimIndent())

		properties.writeText("""
			appGroup = "dev.opensavvy.test"
		""".trimIndent())

		val result = GradleRunner.create()
			.withProjectDir(testProjectDir)
			.withPluginClasspath()
			.withArguments("printVersion", "-PappVersion=1.0.0")
			.build()

		assertTrue(result.output.contains("Version: 1.0.0"), "Output: ${result.output}")
	}

	@Test
	fun versionDefault() {
		settingsFile.writeText("""
			rootProject.name = "hello-world"
		""".trimIndent())

		buildFile.writeText("""
			plugins {
			    id("dev.opensavvy.conventions.base")
			}
			
			tasks.register("printVersion") {
				doLast {
					println("Version: " + project.version)
				}
			}
		""".trimIndent())

		properties.writeText("""
			appGroup = "dev.opensavvy.test"
		""".trimIndent())

		val result = GradleRunner.create()
			.withProjectDir(testProjectDir)
			.withPluginClasspath()
			.withArguments("printVersion")
			.build()

		assertTrue(result.output.contains("Version: 0.0.0-dev"), "Output: ${result.output}")
	}
}
