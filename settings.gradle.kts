/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 * For more detailed information on multi-project builds, please refer to https://docs.gradle.org/8.3/userguide/building_swift_projects.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

rootProject.name = "OpenSavvyGradleConventions"

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS

	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}

pluginManagement {
	includeBuild("base/meta-base")
	includeBuild("plugin/meta-plugin")
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

include(
	"base",
	"plugin",
	"root",
	"kotlin",
	"settings",
)

includeBuild(
	"versions",
)
