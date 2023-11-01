# Settings plugins

## Combined

> Plugin ID: `dev.opensavvy.conventions.settings.all`

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
