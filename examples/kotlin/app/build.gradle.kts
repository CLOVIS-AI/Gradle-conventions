plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.application)
}

kotlin {
	jvm {
		withJava()
	}

	val commonMain by sourceSets.getting {
		dependencies {
			implementation(project(":core"))
		}
	}
}

application {
	mainClass.set("example.MainKt")
}
