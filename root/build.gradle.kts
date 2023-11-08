plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	api(libs.gradle.nexusPublish)
	api(libs.gradle.dokkatoo)

	testImplementation(project(":test-utils"))
}

repositories {
	gradlePluginPortal()
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
