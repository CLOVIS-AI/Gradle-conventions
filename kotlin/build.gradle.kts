
plugins {
	id("dev.opensavvy.conventions.meta.base")
	id("dev.opensavvy.conventions.meta.plugin")
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
	implementation("org.jetbrains.kotlin:kotlin-power-assert:${libs.versions.kotlin.get()}")
	implementation(project(":versions"))

	api(libs.gradle.nmcp)
	implementation(libs.gradle.tapmoc)
	implementation(libs.gradle.dokka)
	implementation(libs.gradle.dokka.mkdocs)
	implementation(libs.gradle.kover)
	compileOnly(gradleKotlinDsl())
}

gradlePlugin {
	plugins {
		create("kotlinBase") {
			id = "dev.opensavvy.conventions.kotlin.base"
			implementationClass = "dev.opensavvy.conventions.kotlin.KotlinBasePlugin"
		}
		create("kotlinInternal") {
			id = "dev.opensavvy.conventions.kotlin.internal"
			implementationClass = "dev.opensavvy.conventions.kotlin.KotlinInternalPlugin"
		}
		create("kotlinApplication") {
			id = "dev.opensavvy.conventions.kotlin.application"
			implementationClass = "dev.opensavvy.conventions.kotlin.KotlinApplicationPlugin"
		}
		create("kotlinAbstractLibrary") {
			id = "dev.opensavvy.conventions.kotlin.abstractLibrary"
			implementationClass = "dev.opensavvy.conventions.kotlin.KotlinAbstractLibraryPlugin"
		}
		create("kotlinLibrary") {
			id = "dev.opensavvy.conventions.kotlin.library"
			implementationClass = "dev.opensavvy.conventions.kotlin.KotlinLibraryPlugin"
		}
	}
}
