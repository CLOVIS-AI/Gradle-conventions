package dev.opensavvy.conventions

plugins {
	id("com.gradleup.nmcp.aggregation")
	id("dev.opensavvy.dokka-mkdocs")
	id("org.jetbrains.dokka")
	id("org.jetbrains.kotlinx.kover")
}

nmcpAggregation {
	val projectPath = System.getenv("CI_PROJECT_PATH_SLUG")
	val refSlug = System.getenv("CI_COMMIT_REF_SLUG")
	val pipelineId = System.getenv("CI_PIPELINE_IID")

	centralPortal {
		username = System.getenv("OSSRH_USERNAME")
		password = System.getenv("OSSRH_PASSWORD")
		publishingType = "AUTOMATIC"
		publicationName = "$projectPath version $refSlug pipeline $pipelineId"
	}
}

val library by configurations.registering {
	isCanBeResolved = false
	isCanBeConsumed = false
}

fun NamedDomainObjectProvider<Configuration>.includeLibraries() {
	this.get().extendsFrom(library.get())
}

configurations.dokka.includeLibraries()
configurations.kover.includeLibraries()
configurations.nmcpAggregation.includeLibraries()
