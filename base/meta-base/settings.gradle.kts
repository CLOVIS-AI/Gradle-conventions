rootProject.name = "meta-base"

dependencyResolutionManagement {
	repositories {
		mavenCentral()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
