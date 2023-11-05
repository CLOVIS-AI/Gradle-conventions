plugins {
	`embedded-kotlin`
	`maven-publish`
}

dependencies {
	api(libs.bundles.prepared)
}

publishing {
	publications {
		register("tests", MavenPublication::class.java) {
			from(components["java"])
		}
	}
}
