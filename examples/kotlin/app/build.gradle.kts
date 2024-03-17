plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
	application
}

kotlin {
	jvm {
		withJava()
	}

	sourceSets.commonMain.dependencies {
		implementation(project(":core"))
	}

	sourceSets.commonTest.dependencies {
		implementation(libs.prepared)
	}
}

application {
	mainClass.set("example.MainKt")
}
