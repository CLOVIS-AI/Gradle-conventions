plugins {
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
	alias(libs.plugins.testBalloon)
}

dependencies {
	testImplementation(libs.prepared.testBalloon)
}

gradlePlugin {
	plugins {
		create("base") {
			id = "dev.opensavvy.conventions.base"
			implementationClass = "dev.opensavvy.conventions.OpenSavvyBasePlugin"
		}
	}
}
