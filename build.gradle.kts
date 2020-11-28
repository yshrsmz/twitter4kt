plugins {
    id("dependencies")
    id("build-helper")
    id(Plugins.versions) version Versions.versions
    id(Plugins.spotless) version Versions.spotless
    id(Plugins.dokka) version Versions.dokka apply false
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

//    pluginManager.withPlugin("kotlin-multiplatform") {
//        val kotlinExtension = project.extensions.getByName("kotlin")
//                as org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
//        val uniqueName = "${project.group}.${project.name}"
//        kotlinExtension.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
//            compilations["main"].kotlinOptions.freeCompilerArgs += listOf("-module-name", uniqueName)
//        }
//    }
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude(
            "**/gen/**/*.*",
            "**/generated/**/*.*",
            "**/testconfig/**/*.*",
            "includedBuild/dependencies/src/main/java/Deps.kt"
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
