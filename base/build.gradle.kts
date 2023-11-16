plugins {
	id("dev.opensavvy.conventions.meta.base")

	`kotlin-dsl`
	`maven-publish`
}

gradlePlugin {
	plugins {
		create("base") {
			id = "dev.opensavvy.conventions.base"
			implementationClass = "dev.opensavvy.conventions.OpenSavvyBasePlugin"
		}
	}
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
