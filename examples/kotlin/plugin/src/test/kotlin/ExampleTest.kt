package example.kotlin

import io.kotest.matchers.string.shouldContain
import opensavvy.prepared.compat.gradle.buildKts
import opensavvy.prepared.compat.gradle.gradle
import opensavvy.prepared.compat.gradle.settingsKts
import opensavvy.prepared.runner.kotest.PreparedSpec
import opensavvy.prepared.suite.config.CoroutineTimeout
import kotlin.time.Duration.Companion.minutes

@Suppress("unused")
class ExampleTest : PreparedSpec({
    test("The group is applied correctly", config = CoroutineTimeout(1.minutes)) {
        gradle.settingsKts("""
            rootProject.name "test"
        """.trimIndent())

        gradle.rootProject.buildKts("""
            plugins {
                id("example.kotlin.example")
            }
            
            tasks.register("logGroup") {
                doLast {
                    println("Group: " + project.group)
                }
            }
        """.trimIndent())

        val output = gradle.runner()
            .withPluginClasspath()
            .withArguments("logGroup")
            .build()

        output.output shouldContain "Group: test"
    }
})
