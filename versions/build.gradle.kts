plugins {
	`embedded-kotlin`
}

val appVersion: String? by project

allprojects {
	version = appVersion ?: "0.0.0-dev"
	group = "dev.opensavvy.gradle.conventions"

	repositories {
		mavenCentral()
	}
}

val createVersion by tasks.registering {
	description = "Store the self version into the resources"

	val file = project.layout.projectDirectory.file("src/main/kotlin/Self.kt").asFile

	doLast {
		file.writeText("""
			package dev.opensavvy.conventions.versions
			
			const val OPENSAVVY_CONVENTIONS_VERSION = "$version"
		""".trimIndent())
	}

	inputs.property("version", version)
	outputs.file(file)
}

tasks.compileKotlin {
	dependsOn(createVersion)
}
