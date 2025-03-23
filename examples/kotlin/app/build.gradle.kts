plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
	alias(opensavvyConventions.plugins.aligned.composeMultiplatform)
	alias(opensavvyConventions.plugins.aligned.composeCompiler)
}

kotlin {
	jvm {
		binaries {
			executable {
				mainClass.set("example.MainKt")
			}
		}
	}

	sourceSets.commonMain.dependencies {
		implementation(project(":core"))

		implementation(compose.runtime)
	}

	sourceSets.commonTest.dependencies {
		implementation(libs.prepared)
	}
}
