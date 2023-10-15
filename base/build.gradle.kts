plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	testImplementation(libs.junit5.jupiter)
	testRuntimeOnly(libs.junit5.launcher)
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
