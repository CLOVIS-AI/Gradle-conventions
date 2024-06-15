# Kotlin plugins

## Base

> Plugin ID: `dev.opensavvy.conventions.kotlin`

- Applies the [Kotlin Multiplatform plugin](https://plugins.gradle.org/plugin/org.jetbrains.kotlin.multiplatform)

## Library

> Plugin ID: `dev.opensavvy.conventions.kotlin.library`

- Configures the project to be compatible with the JRE we selected for library
- Configures publishing to the GitLab Maven Registry
- Configures publishing to Maven Central
- Configures documentation generation with Dokkatoo
- Configures the Power Assert plugin

## Abstract Library

> Plugin ID: `dev.opensavvy.conventions.kotlin.abstractLibrary`

Configures everything the Library plugin does, but without applying the Kotlin plugin.
This makes it usable for publishing other kinds of artifacts, like Gradle plugins.

## Application

> Plugin ID: `dev.opensavvy.conventions.kotlin.application`

- Configures the project to be compatible with the JRE we selected for applications

## Internal

> Plugin ID: `dev.opensavvy.conventions.kotlin.internal`

This plugin is meant to be used for internal modules that represent shared code between application modules (like libraries), but are not meant to be published (like applications).
