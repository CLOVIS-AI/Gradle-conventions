plugins {
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

dependencies {
	// Necessary for auto-complete
	// Substituted at runtime by kotlin-dsl
	compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
}

gradlePlugin {
	plugins {
		create("plugin") {
			id = "dev.opensavvy.conventions.plugin"
			implementationClass = "dev.opensavvy.conventions.OpenSavvyPluginPlugin"
		}
	}
}
