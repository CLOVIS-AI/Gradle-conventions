package dev.opensavvy.conventions

import io.kotest.matchers.string.shouldContain
import opensavvy.prepared.compat.filesystem.div
import opensavvy.prepared.compat.gradle.buildKts
import opensavvy.prepared.compat.gradle.gradle
import opensavvy.prepared.compat.gradle.settingsKts
import opensavvy.prepared.suite.SuiteDsl
import opensavvy.prepared.suite.prepared
import kotlin.io.path.writeText

fun SuiteDsl.baseTests() = suite("Base plugin") {
	val properties by prepared {
		(gradle.dir / "gradle.properties")()
	}

	suite("Group") {
		test("Should fail if the group is not configured") {
			gradle.settingsKts("""
				rootProject.name = "hello-world"
			""".trimIndent())

			gradle.rootProject.buildKts("""
				plugins {
					id("dev.opensavvy.conventions.base")
				}
			""".trimIndent())

			val result = gradle.runner()
				.withPluginClasspath()
				.withArguments("build")
				.buildAndFail()

			result.output shouldContain "Missing group declaration; you should add 'appGroup=<your group name>' in the gradle.properties file"
		}

		test("Should use the configured group") {
			gradle.settingsKts("""
				rootProject.name = "hello-world"
			""".trimIndent())

			gradle.rootProject.buildKts("""
				plugins {
					id("dev.opensavvy.conventions.base")
				}

				tasks.register("printGroup") {
					doLast {
						println("Group: " + project.group)
					}
				}
			""".trimIndent())

			properties().writeText("""
				appGroup = dev.opensavvy.test
			""".trimIndent())

			val result = gradle.runner()
				.withPluginClasspath()
				.withArguments("printGroup")
				.build()

			result.output shouldContain "Group: dev.opensavvy.test"
		}
	}

	suite("Version") {
		test("Should use the configured version") {
			gradle.settingsKts("""
				rootProject.name = "hello-world"
			""".trimIndent())

			gradle.rootProject.buildKts("""
				plugins {
					id("dev.opensavvy.conventions.base")
				}

				tasks.register("printVersion") {
					doLast {
						println("Version: " + project.version)
					}
				}
			""".trimIndent())

			properties().writeText("""
				appGroup = dev.opensavvy.test
			""".trimIndent())

			val result = gradle.runner()
				.withPluginClasspath()
				.withArguments("printVersion", "-PappVersion=1.0.0")
				.build()

			result.output shouldContain "Version: 1.0.0"
		}

		test("A default version should be configured") {
			gradle.settingsKts("""
				rootProject.name = "hello-world"
			""".trimIndent())

			gradle.rootProject.buildKts("""
				plugins {
					id("dev.opensavvy.conventions.base")
				}

				tasks.register("printVersion") {
					doLast {
						println("Version: " + project.version)
					}
				}
			""".trimIndent())

			properties().writeText("""
				appGroup = dev.opensavvy.test
			""".trimIndent())

			val result = gradle.runner()
				.withPluginClasspath()
				.withArguments("printVersion")
				.build()

			result.output shouldContain "Version: DEV"
		}
	}
}
