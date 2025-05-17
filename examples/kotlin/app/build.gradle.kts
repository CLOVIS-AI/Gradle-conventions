plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
	alias(shared.plugins.compose.compiler)
	alias(shared.plugins.compose.multiplatform)
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
		implementation(shared.prepared)
	}
}
