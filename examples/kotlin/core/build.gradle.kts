plugins {
	alias(opensavvyConventions.plugins.base)
	alias(opensavvyConventions.plugins.kotlin.library)
	alias(shared.plugins.testBalloon)
}

kotlin {
	jvm()
	js {
		browser()
		nodejs()
	}
	linuxX64()
	linuxArm64()
	macosArm64()
	iosArm64()
	iosSimulatorArm64()
	watchosArm64()
	watchosSimulatorArm64()
	tvosArm64()
	tvosSimulatorArm64()
	mingwX64()
	wasmJs {
		browser()
		nodejs()
	}
	wasmWasi {
		nodejs()
	}

	sourceSets.commonTest.dependencies {
		implementation(shared.prepared.testBalloon)
		implementation("org.jetbrains.kotlin:kotlin-test:${shared.versions.kotlin.get()}")
	}
}

library {
	name.set("Kotlin Sample: Core")
	description.set("Just a normal sample")
	homeUrl.set("https://gitlab.com/opensavvy/automation/gradle-conventions")

	license.set {
		name.set("Apache 2.0")
		url.set("https://www.apache.org/licenses/LICENSE-2.0")
	}

	coverage.set(100)
}
