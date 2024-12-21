plugins {
	alias(opensavvyConventions.plugins.base)
	id("dev.opensavvy.dokka-mkdocs")
}

dependencies {
	dokka(project(":core"))
	dokka(project(":app"))
}
