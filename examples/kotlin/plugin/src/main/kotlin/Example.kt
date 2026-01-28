package example.kotlin

import org.gradle.api.Plugin
import org.gradle.api.Project

class Example : Plugin<Project> {

	override fun apply(target: Project) {
		target.group = "test"
	}

}
