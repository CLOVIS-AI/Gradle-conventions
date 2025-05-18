plugins {
	alias(opensavvyConventions.plugins.root)
	`kotlin-dsl` apply false
	alias(shared.plugins.kotlin) apply false
}

dependencies {
	dokka(project(":core"))
	kover(project(":core"))
}
