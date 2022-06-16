plugins {
    `java-gradle-plugin`
    `maven-publish`
    id("ca.cutterslade.analyze") version "1.9.0"
    id("com.asarkar.gradle.build-time-tracker") version "4.3.0"
    id("com.github.ben-manes.versions") version "0.42.0"
    id("com.gradle.plugin-publish") version "0.21.0"
    id("me.qoomon.git-versioning") version "6.1.4"
    id("se.ascp.gradle.gradle-versions-filter") version "0.1.16"
}

group = "io.github.ngyewch.gradle"
version = "v0.0.0-SNAPSHOT"
gitVersioning.apply {
    refs {
        tag("v(?<version>.*)") {
            considerTagsOnBranches = true
            version = "\${ref.version}"
        }
        branch(".+") {
            version = "\${ref}-SNAPSHOT"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(gradleApi())

    implementation("org.eclipse.jgit:org.eclipse.jgit:6.2.0.202206071550-r")
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("gradle-git-describe-plugin") {
            id = "io.github.ngyewch.git-describe"
            displayName = "Gradle git describe plugin"
            description = "Gradle git describe plugin."
            implementationClass = "com.github.ngyewch.gradle.GitDescribePlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/ngyewch/gradle-git-describe-plugin"
    vcsUrl = "https://github.com/ngyewch/gradle-git-describe-plugin.git"
    tags = listOf("git")
}
