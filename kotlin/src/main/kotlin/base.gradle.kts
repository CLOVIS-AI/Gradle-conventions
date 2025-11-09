package dev.opensavvy.conventions.kotlin

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
	kotlin("multiplatform")
	id("org.jetbrains.kotlin.plugin.power-assert")
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()

	testLogging {
		showExceptions = false

		val reporter = TestOutputReporter()
		beforeTest(KotlinClosure1<TestDescriptor, Unit>({
			reporter.beforeTest(this)
		}))
		onOutput(KotlinClosure2<TestDescriptor, TestOutputEvent, Unit>({ descriptor, event ->
			reporter.reportTest(descriptor, event)
		}))
		afterTest(KotlinClosure2<TestDescriptor, TestResult, Unit>({ descriptor, result ->
			reporter.afterTest(descriptor, result)
		}))
	}
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
	functions = listOf(
		// Standard library
		"kotlin.assert",
		"kotlin.require",
		"kotlin.requireNotNull",
		"kotlin.check",
		"kotlin.checkNotNull",

		// Standard test library
		"kotlin.test.assertTrue",
		"kotlin.test.assertFalse",
		"kotlin.test.assertEquals",
		"kotlin.test.assertNull",

		// Prepared
		"opensavvy.prepared.suite.assertions.log",
	)
}

private class TestOutputReporter {
	private val tests = HashMap<TestDescriptor, ArrayList<TestOutputEvent>>()
	private val reportingLock = Any()

	fun beforeTest(descriptor: TestDescriptor) = synchronized(this) {
		tests[descriptor] = arrayListOf()
	}

	fun reportTest(descriptor: TestDescriptor, event: TestOutputEvent) {
		synchronized(this) { tests.computeIfAbsent(descriptor) { arrayListOf() } }
			.add(event)
	}

	fun afterTest(descriptor: TestDescriptor, result: TestResult) {
		val events = synchronized(this) { tests.remove(descriptor) }
			?: return

		if (result.resultType != TestResult.ResultType.FAILURE)
			return

		val testName = generateSequence(descriptor) { it.parent }
			.toList()
			.reversed()
			.joinToString(" › ") { it.displayName }

		synchronized(reportingLock) {
			println("> $testName")
			for (event in events) {
				println(event.message)
			}
			println("> Thrown during execution:")
			for (exception in result.exceptions) {
				println(exception.stackTraceToString())
			}
		}
	}
}
