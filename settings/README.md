# Settings plugins

## Combined

> Plugin ID: `dev.opensavvy.conventions.settings`

Applies all the other plugins in this file, to avoid specifying them one by one.

## Build Cache

> Plugin ID: `dev.opensavvy.conventions.settings.build-cache`

- Configures the project to access the OpenSavvy Build Cache.
- The cache will be automatically written to if we're running on the default branch (detected using GitLab Predefined variables), using the `GRADLE_BUILD_CACHE_CREDENTIALS` environment variable
- Otherwise, the cache is read-only
- Override the Build Cache URL using `buildCacheUrl` Gradle property

## Plugin Catalog

> Plugin ID: `dev.opensavvy.conventions.settings.plugin-catalog`

- Creates a version catalog with all the plugins contained in this repository

## Java Toolchains

> Plugin ID: `dev.opensavvy.conventions.settings.java-toolchains`

- Applies the [FooJay Resolver](https://github.com/gradle/foojay-toolchains)

## Gradle Enterprise

> Plugin ID: `dev.opensavvy.conventions.settings.enterprise`

- Active only when the environment variable  `OPENSAVVY_GRADLE_ENTERPRISE` is set to `true`
- Applies the [Gradle Develocity plugin](https://docs.gradle.com/develocity/gradle-plugin/current/)
- Accepts the [Gradle terms of service](https://gradle.com/terms-of-service)
- Automatically publishes a build scan when a job fails in CI
