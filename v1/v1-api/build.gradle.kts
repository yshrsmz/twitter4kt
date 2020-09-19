import java.util.*

plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { url = uri("https://kotlin.bintray.com/kotlinx/") }
}
kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    /*
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMacOs = hostOs == "Mac OS X"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        isMacOs -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    if (isMacOs) {
        ios()
    }
    */

    sourceSets {
        val ktorVersion = "1.4.0"
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.0")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                api(project(":v1:v1-model"))
            }

        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
            val genDir = file("${project.buildDir}/testconfig")
            this.kotlin.srcDirs(genDir)
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        /*
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
         */
    }
}

// TODO: make it a gradle plugin
tasks.create("createTestConfig") {
    val secretsFile = rootDir.resolve("secrets.properties")
    val secretProps = Properties()
    secretProps.load(secretsFile.inputStream())

    val outputDir = file("${project.buildDir}/testconfig")
    val consumerKey = secretProps["twitter_consumer_key"]
    val consumerSecret = secretProps["twitter_consumer_secret"]

    inputs.property("consumerKey", consumerKey)
    inputs.property("consumerSecret", consumerSecret)
    outputs.dir(outputDir)

    doLast {
        val configFile = file("$outputDir/com/codingfeline/twitter4kt/TestConfig.kt")
        configFile.parentFile.mkdirs()
        configFile.writeText(
            """// Generated file. Do not edit!
            |package com.codingfeline.twitter4kt
            |
            |val TEST_CONSUMER_KEY = "$consumerKey"
            |val TEST_CONSUMER_SECRET = "$consumerSecret"
        """.trimMargin()
        )
    }
}

// TODO depends on test tasks
tasks.getByName("compileKotlinJvm").dependsOn("createTestConfig")
