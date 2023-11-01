package dev.opensavvy.conventions

import io.kotest.core.spec.style.StringSpec
import opensavvy.prepared.runner.kotest.preparedSuite

class BasePlugins : StringSpec({
	preparedSuite {
		baseTests()
	}
})
