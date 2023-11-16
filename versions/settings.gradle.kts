rootProject.name = "versions"

pluginManagement {
	includeBuild("../base/meta-base")
	includeBuild("../plugin/meta-plugin")
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}
