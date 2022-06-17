# gradle-git-describe-plugin

![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/ngyewch/gradle-git-describe-plugin)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/ngyewch/gradle-git-describe-plugin/Java%20CI)

A Gradle plugin for setting the project version to the value returned by `git describe --tags`

## Installation

See https://plugins.gradle.org/plugin/io.github.ngyewch.git-describe

## Overview

If `project.version` is already set, this plugin does nothing. Example:
```
# omit the 'v' prefix
./gradlew build -Pversion=1.2.3
```

Else if the environment variable `GIT_DESCRIBE_VERSION` is set, it is used as the value for `project.version` (the 'v' prefix is removed):
```
# specify the 'v' prefix
GIT_DESCRIBE_VERSION=v1.2.3 ./gradlew build
```

Else if `git describe --tags --match 'v*' --dirty` returns a value, it is used as the value for `project.version` (the 'v' prefix is removed).

Else if the environment variable `DEFAULT_GIT_DESCRIBE_VERSION` is set, it is used as the value for `project.version` (the 'v' prefix is removed):
```
# specify the 'v' prefix
DEFAULT_GIT_DESCRIBE_VERSION=v1.0.0 ./gradlew build
```
