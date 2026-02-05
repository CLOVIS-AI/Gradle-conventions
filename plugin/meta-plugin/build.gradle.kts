plugins {
	`kotlin-dsl`
	alias(libs.plugins.tapmoc)
}

tapmoc {
	java(11)
}

sourceSets.main.configure {
	kotlin.srcDir("../src/main/kotlin")
}

group = "dev.opensavvy.gradle.conventions"

gradlePlugin {
	plugins {
		create("kotlin") {
			id = "dev.opensavvy.conventions.meta.plugin"
			implementationClass = "dev.opensavvy.conventions.OpenSavvyPluginPlugin"
		}
	}
}

dependencies {
	// Necessary for auto-complete
	// Substituted at runtime by kotlin-dsl
	compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:${embeddedKotlinVersion}")
	implementation(libs.gradle.tapmoc)
}

