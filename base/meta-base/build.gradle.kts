plugins {
	kotlin("jvm") version libs.versions.kotlin.get()
	`java-gradle-plugin`
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
			id = "dev.opensavvy.conventions.meta.base"
			implementationClass = "dev.opensavvy.conventions.OpenSavvyBasePlugin"
		}
	}
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
}
