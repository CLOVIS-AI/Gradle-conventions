package example

import opensavvy.prepared.runner.kotest.PreparedSpec

@Suppress("unused")
class HelloWorldTest : PreparedSpec({
	// These tests are bad and are typical of tests written to pass coverage checks
	// rather than actually verify the quality of the software.
	// But oh well, this example is just about checking the coverage tool is actually enabled, so too bad.

	test("Call the 'foo' function") {
		foo()
	}

	test("Create an instance of the HelloWorld class") {
		check(HelloWorld() != null)
	}
})
