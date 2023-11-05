package dev.opensavvy.conventions.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class JavaToolchains : Plugin<Settings> {

    override fun apply(target: Settings) {
        target.plugins.apply("org.gradle.toolchains.foojay-resolver-convention")
    }
}
