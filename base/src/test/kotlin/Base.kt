package dev.opensavvy.conventions

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
		test("Should warn if the group is not configured") {
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
				.build()

			check("Missing group declaration; you should add 'appGroup=<your group name>' in the gradle.properties file" in result.output)
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

			check("Group: dev.opensavvy.test" in result.output)
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

			check("Version: 1.0.0" in result.output)
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

			check("Version: DEV" in result.output)
		}
	}
}
