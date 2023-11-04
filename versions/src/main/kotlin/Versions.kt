package dev.opensavvy.conventions.versions

object Versions {
	/**
	 * Version of Kotlin we use in our plugins.
	 */
	const val KOTLIN = "1.9.10"

	/**
	 * Version of Java used when creating libraries.
	 *
	 * Because all users of the libraries will need to have at least this version of Java to import the library,
	 * we will intentionally delay upgrading this version.
	 */
	const val JAVA_COMPAT = 11

	/**
	 * Version of Java used when creating final projects.
	 *
	 * In end-user projects, we control the Java version used during execution, so we can use the latest version
	 * without having to keep compatibility for older versions.
	 */
	const val JAVA_APP = 21
}
