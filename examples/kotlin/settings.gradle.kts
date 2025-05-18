rootProject.name = "Kotlin-example"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }

    versionCatalogs {
        create("shared") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
}

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
    "mkdocs",
)

includeBuild("../..") // if using this in a real project, remove this
