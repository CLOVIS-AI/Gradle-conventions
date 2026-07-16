plugins {
	id("org.jetbrains.kotlin.jvm") version libs.versions.kotlin.get()
	`java-gradle-plugin`
	id("org.jetbrains.kotlin.plugin.sam.with.receiver") version libs.versions.kotlin.get()
	alias(libs.plugins.tapmoc)
}

tapmoc {
	java(11)
}

sourceSets.main.configure {
	kotlin.srcDir("../src/main/kotlin")
}

group = "dev.opensavvy.gradle.conventions"

gradlePlugin {
	plugins {
		create("kotlin") {
			id = "dev.opensavvy.conventions.meta.plugin"
			implementationClass = "dev.opensavvy.conventions.OpenSavvyPluginPlugin"
		}
	}
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
	implementation("org.jetbrains.kotlin:kotlin-sam-with-receiver:${libs.versions.kotlin.get()}")
	implementation(libs.gradle.tapmoc)
}

samWithReceiver {
	annotation(HasImplicitReceiver::class.qualifiedName!!)
}
