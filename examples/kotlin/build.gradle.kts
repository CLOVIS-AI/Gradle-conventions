plugins {
	alias(opensavvyConventions.plugins.root)
	`kotlin-dsl` apply false
	alias(shared.plugins.kotlin) apply false
}

dependencies {
	library(project(":core"))
}
