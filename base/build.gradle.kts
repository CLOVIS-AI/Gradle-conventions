plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	testImplementation(project(":test-utils"))
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
