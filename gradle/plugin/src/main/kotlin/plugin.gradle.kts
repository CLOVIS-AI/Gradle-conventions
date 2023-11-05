package dev.opensavvy.conventions

plugins {
	`maven-publish`
}

val appVersion: String? by project

version = appVersion ?: "0.0.0-dev"
group = "dev.opensavvy.gradle.conventions"

repositories {
	mavenCentral()
	gradlePluginPortal()
}

// When running in GitLab CI, uses the auto-created CI variables to configure the GitLab Maven Registry.
// For more information on the variables and their values, see:
// - https://docs.gitlab.com/ee/user/packages/maven_repository/
// - https://docs.gitlab.com/ee/ci/variables/predefined_variables.html
publishing {
	repositories {
		val projectId = System.getenv("CI_PROJECT_ID") ?: return@repositories
		val token = System.getenv("CI_JOB_TOKEN") ?: return@repositories
		val api = System.getenv("CI_API_V4_URL") ?: return@repositories

		maven {
			name = "GitLab"
			url = uri("$api/projects/$projectId/packages/maven")

			credentials(HttpHeaderCredentials::class.java) {
				name = "Job-Token"
				value = token
			}

			authentication {
				create<HttpHeaderAuthentication>("header")
			}
		}
	}
}
