plugins {
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
	implementation("org.jetbrains.kotlin:kotlin-sam-with-receiver:${libs.versions.kotlin.get()}")
	implementation(libs.gradle.tapmoc)
}

gradlePlugin {
	plugins {
		create("plugin") {
			id = "dev.opensavvy.conventions.plugin"
			implementationClass = "dev.opensavvy.conventions.OpenSavvyPluginPlugin"
		}
	}
}
