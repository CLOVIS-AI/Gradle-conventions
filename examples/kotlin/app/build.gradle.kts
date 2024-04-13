plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
	alias(opensavvyConventions.plugins.aligned.composeMultiplatform)
	application
}

kotlin {
	jvm {
		withJava()
	}

	sourceSets.commonMain.dependencies {
		implementation(project(":core"))

		implementation(compose.runtime)
	}

	sourceSets.commonTest.dependencies {
		implementation(libs.prepared)
	}
}

application {
	mainClass.set("example.MainKt")
}
