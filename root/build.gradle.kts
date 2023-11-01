plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	api(project(":test-utils"))

	api(libs.gradle.nexusPublish)
}

repositories {
	gradlePluginPortal()
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
