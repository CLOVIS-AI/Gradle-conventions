plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	implementation(project(":versions"))
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
