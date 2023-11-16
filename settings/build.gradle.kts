plugins {
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

dependencies {
	implementation("dev.opensavvy.gradle.conventions:versions:$version")

	implementation(libs.gradle.foojayResolver)
}

gradlePlugin {
	plugins {
		create("combined") {
			id = "dev.opensavvy.conventions.settings"
			implementationClass = "dev.opensavvy.conventions.settings.Combined"
		}

		create("build-cache") {
			id = "dev.opensavvy.conventions.settings.build-cache"
			implementationClass = "dev.opensavvy.conventions.settings.BuildCache"
		}

		create("plugin-catalog") {
			id = "dev.opensavvy.conventions.settings.plugin-catalog"
			implementationClass = "dev.opensavvy.conventions.settings.ConventionCatalog"
		}

		create("java-toolchains") {
			id = "dev.opensavvy.conventions.settings.java-toolchains"
			implementationClass = "dev.opensavvy.conventions.settings.JavaToolchains"
		}
	}
}
