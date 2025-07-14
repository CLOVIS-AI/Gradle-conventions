
plugins {
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

dependencies {
	api(libs.gradle.nmcp)
	api(libs.gradle.dokka)
	api(libs.gradle.dokka.mkdocs)
	api(libs.gradle.kover)
}
