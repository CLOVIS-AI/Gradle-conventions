package dev.opensavvy.conventions

import io.kotest.core.spec.style.StringSpec
import opensavvy.prepared.runner.kotest.preparedSuite
import opensavvy.prepared.suite.config.CoroutineTimeout
import kotlin.time.Duration.Companion.minutes

class BasePlugins : StringSpec({
	preparedSuite(CoroutineTimeout(1.minutes)) {
		baseTests()
	}
})
