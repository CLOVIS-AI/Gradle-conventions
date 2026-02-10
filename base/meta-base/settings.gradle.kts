rootProject.name = "meta-base"

dependencyResolutionManagement {
	repositories {
		mavenCentral()
	}
}

dependencyResolutionManagement {
	versionCatalogs {
		create("libs") {
			from(files("../../gradle/libs.versions.toml"))
		}
	}
}