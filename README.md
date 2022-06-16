# gradle-git-describe-plugin

![GitHub release (latest by date)](https://img.shields.io/github/v/release/ngyewch/gradle-git-describe-plugin)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/ngyewch/gradle-git-describe-plugin/Java%20CI)

A Gradle plugin for setting the project version to the value returned by `git describe --tags`

## Basic usage

Kotlin DSL (`build.gradle.kts`)

```
plugins {
    java
    id("io.github.ngyewch.git-describe") version "0.1.0"
}
```

## Advanced usage

The project version used can also be specified via (in order of precedence):
* Project property
    ```
    ./gradlew build -Pversion=v1.0.0
    ```
* Environment variable
    ```
    GIT_DESCRIBE_VERSION=v1.0.0 ./gradlew build
    ```
  
The default project version can be specified via an environment variable

```
DEFAULT_GIT_DESCRIBE_VERSION=v0.0.0 ./gradlew build
```
