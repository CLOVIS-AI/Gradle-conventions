plugins {
	`kotlin-dsl`
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

repositories {
	mavenCentral()
}
