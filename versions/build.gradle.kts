plugins {
	`kotlin-dsl`
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

val createVersion by tasks.registering(EmbedVersionTask::class) {
	sources.set(project.layout.projectDirectory.file("src/").toString())
	this.version = project.version.toString()
}

abstract class EmbedVersionTask : DefaultTask() {

	@get:Input
	abstract val sources: Property<String>

	@get:Internal
	val writePath: Provider<String>
		get() = sources.map { "$it/main/kotlin/Self.kt" }

	@get:Input
	abstract val version: Property<String>

	init {
		description = "Store the self version into the resources"

		outputs.file(writePath)
	}

	@TaskAction
	fun embedVersion() {
		File(writePath.get()).writeText("""
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
