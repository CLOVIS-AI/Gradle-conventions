plugins {
	`kotlin-dsl`
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

val createVersion by tasks.registering(EmbedVersionTask::class) {
	writePath.set(project.layout.projectDirectory.file("src/main/kotlin/Self.kt"))
	version = project.version.toString()
}

abstract class EmbedVersionTask : DefaultTask() {

	@get:OutputFile
	abstract val writePath: RegularFileProperty

	@get:Input
	abstract val version: Property<String>

	init {
		description = "Store the self version into the resources"
	}

	@TaskAction
	fun embedVersion() {
		writePath.get().asFile.writeText("""
			package dev.opensavvy.conventions.versions
			
			const val OPENSAVVY_CONVENTIONS_VERSION = "${version.get()}"
		""".trimIndent())
	}
}

tasks.compileKotlin {
	dependsOn(createVersion)
}

tasks.sourcesJar {
	dependsOn(createVersion)
}

publishing {
	publications {
		register("versions", MavenPublication::class.java) {
			from(components["java"])
		}
	}
}
