plugins {
	alias(opensavvyConventions.plugins.root)
	`kotlin-dsl` apply false
}

dependencies {
	dokka(project(":core"))
	kover(project(":core"))
}
