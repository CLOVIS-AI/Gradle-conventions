package dev.opensavvy.conventions

plugins {
	id("io.github.gradle-nexus.publish-plugin")
	id("dev.opensavvy.dokka-mkdocs")
	id("org.jetbrains.dokka")
	id("org.jetbrains.kotlinx.kover")
}

nexusPublishing {
	val projectPath = System.getenv("CI_PROJECT_PATH_SLUG")
	val refSlug = System.getenv("CI_COMMIT_REF_SLUG")
	val pipelineId = System.getenv("CI_PIPELINE_IID")
	repositoryDescription.set("$projectPath for $refSlug (pipeline $pipelineId)") // must be globally unique for a pipeline to avoid issues with concurrent builds

	repositories {
		sonatype {
			nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
			snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

			username.set(System.getenv("OSSRH_USERNAME"))
			password.set(System.getenv("OSSRH_PASSWORD"))
		}
	}
}
