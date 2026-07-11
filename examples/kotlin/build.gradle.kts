plugins {
	alias(opensavvyConventions.plugins.root)
	alias(shared.plugins.kotlin) apply false
}

dependencies {
	library(project(":core"))
}
