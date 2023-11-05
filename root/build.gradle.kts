plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	api(project(":test-utils"))

	api(libs.gradle.nexusPublish)
	api(libs.gradle.dokkatoo)
}

repositories {
	gradlePluginPortal()
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
