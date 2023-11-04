plugins {
	`embedded-kotlin`
}

val appVersion: String? by project

allprojects {
	version = appVersion ?: "0.0.0-dev"
	group = "dev.opensavvy.gradle.conventions"

	repositories {
		mavenCentral()
	}
}
