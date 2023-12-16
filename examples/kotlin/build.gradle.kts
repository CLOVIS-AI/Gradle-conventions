plugins {
	alias(opensavvyConventions.plugins.root)
	`kotlin-dsl` apply false
}

dependencies {
	dokkatoo(project(":core"))
}
