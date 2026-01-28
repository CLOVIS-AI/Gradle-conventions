rootProject.name = "meta-plugin"

dependencyResolutionManagement {
	repositories {
		mavenCentral()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
