plugins {
    id(Plugins.buildHelper)
    id(Plugins.versions) version Versions.versions
    id(Plugins.spotless) version Versions.spotless
    id(Plugins.dokka) version Versions.kotlin apply false
    id(Plugins.multiplatform) version Versions.kotlin apply false
    id(Plugins.serialization) version Versions.kotlin apply false
}

val GROUP: String by project
val VERSION_NAME: String by project

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
//        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude(
            "**/gen/**/*.*",
            "**/generated/**/*.*",
            "**/testconfig/**/*.*",
            "buildSrc/src/main/java/Dependencies.kt"
        )
        ktlint(Versions.ktlint)
        trimTrailingWhitespace()
        endWithNewline()
        licenseHeaderFile(file("./license-header.txt"))
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint(Versions.ktlint)
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.wrapper {
    gradleVersion = Versions.gradle
    distributionType = Wrapper.DistributionType.ALL
}
