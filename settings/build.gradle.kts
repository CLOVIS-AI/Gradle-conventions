plugins {
	`kotlin-dsl`
	`maven-publish`
}

group = "dev.opensavvy.gradle.conventions"

repositories {
	mavenCentral()
}

gradlePlugin {
	plugins {
		create("build-cache") {
			id = "dev.opensavvy.conventions.settings.build-cache"
			implementationClass = "dev.opensavvy.conventions.settings.BuildCache"
		}

		create("plugin-catalog") {
			id = "dev.opensavvy.conventions.settings.plugin-catalog"
			implementationClass = "dev.opensavvy.conventions.settings.ConventionCatalog"
		}
	}
}
