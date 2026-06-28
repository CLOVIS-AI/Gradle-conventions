package dev.opensavvy.conventions

import nmcp.NmcpAggregationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class OpenSavvyRootPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply("com.gradleup.nmcp.aggregation")
		project.pluginManager.apply("dev.opensavvy.dokka-mkdocs")
		project.pluginManager.apply("org.jetbrains.dokka")
		project.pluginManager.apply("org.jetbrains.kotlinx.kover")

		project.extensions.getByType(NmcpAggregationExtension::class.java).apply {
			val projectPath = System.getenv("CI_PROJECT_PATH_SLUG")
			val refSlug = System.getenv("CI_COMMIT_REF_SLUG")
			val pipelineId = System.getenv("CI_PIPELINE_IID")

			centralPortal {
				it.username.set(System.getenv("OSSRH_USERNAME"))
				it.password.set(System.getenv("OSSRH_PASSWORD"))
				it.publishingType.set("AUTOMATIC")
				it.publicationName.set("$projectPath version $refSlug pipeline $pipelineId")
			}
		}

		val libraries = project.configurations.register("library") {
			it.isCanBeResolved = false
			it.isCanBeConsumed = false
		}

		project.configurations.getByName("dokka").extendsFrom(libraries.get())
		project.configurations.getByName("kover").extendsFrom(libraries.get())
		project.configurations.getByName("nmcpAggregation").extendsFrom(libraries.get())
	}
}
