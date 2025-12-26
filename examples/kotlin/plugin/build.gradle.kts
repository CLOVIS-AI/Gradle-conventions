plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.plugin)
	alias(opensavvyConventions.plugins.kotlin.abstractLibrary)
}

gradlePlugin {
	plugins {
		create("plugin") {
			id = "example.kotlin.example"
			implementationClass = "example.kotlin.Example"
		}
	}
}

library {
	name.set("Kotlin Sample: Gradle Plugin")
	description.set("Just a normal sample")
	homeUrl.set("https://gitlab.com/opensavvy/automation/gradle-conventions")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0")
	}
}
