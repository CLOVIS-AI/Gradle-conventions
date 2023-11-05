plugins {
	`embedded-kotlin`
	`maven-publish`

	id("dev.opensavvy.conventions.plugin")
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

kotlin {
	jvmToolchain(8)
}

publishing {
	publications {
		register("versions", MavenPublication::class.java) {
			from(components["java"])
		}
	}
}
