plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
}

kotlin {
	jvm()
	linuxX64()
}

library {
	name.set("Kotlin Sample: Core")
	description.set("Just a normal sample")
	homeUrl.set("https://gitlab.com/opensavvy/automation/gradle-conventions")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0")
	}
}
