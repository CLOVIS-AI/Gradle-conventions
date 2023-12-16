rootProject.name = "Kotlin-example"

pluginManagement {
    includeBuild("../..") // if using this in a real project, remove this
}

plugins {
    id("dev.opensavvy.conventions.settings") version "<add a version number here>"
}

include(
    "core",
    "app",
    "plugin",
)

includeBuild("../..") // if using this in a real project, remove this
