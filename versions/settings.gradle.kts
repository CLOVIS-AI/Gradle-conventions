rootProject.name = "versions"

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS

	repositories {
		mavenCentral()
	}
}

pluginManagement {
	includeBuild("../base/meta-base")
	includeBuild("../plugin/meta-plugin")
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}
