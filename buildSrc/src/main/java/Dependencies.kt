object Versions {
    const val kotlin = "1.4.10"
    const val mavenPublish = "0.13.0"
    const val versions = "0.33.0"
    const val gradle = "6.6.1"

    const val ktor = "1.4.1"
}

object Plugins {
    const val buildHelper = "build-helper"
    const val versions = "com.github.ben-manes.versions"
    const val mavenPublish = "com.vanniktech.maven.publish"
    const val dokka = "org.jetbrains.dokka"
    const val multiplatform = "org.jetbrains.kotlin.multiplatform"
    const val serialization = "org.jetbrains.kotlin.plugin.serialization"
}

object Deps {

    object Twitter4kt {
        const val coreApi = ":core:core-api"
        const val coreModel = ":core:core-model"

        const val v1Api = ":v1:v1-api"
        const val v1Model = ":v1:v1-model"
    }

    object Kotlin {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.1.0"

        object Test {
            const val common = "test-common"
            const val annotations = "test-annotations-common"
            const val junit = "test-junit"
        }
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val json = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val okhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    }
}