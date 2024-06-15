plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
	alias(opensavvyConventions.plugins.aligned.kotest)
}

kotlin {
	jvm()
	js(IR) {
		browser()
	}
	linuxX64()

	sourceSets.commonTest.dependencies {
		implementation(libs.prepared)
	}
}

library {
	name.set("Kotlin Sample: Core")
	description.set("Just a normal sample")
	homeUrl.set("https://gitlab.com/opensavvy/automation/gradle-conventions")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0")
	}

	coverage.set(100)
}
