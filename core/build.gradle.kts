plugins {
    kotlin("multiplatform") version "1.4.0-rc"
    kotlin("plugin.serialization") version "1.3.70"
}
group = "com.codingfeline"
version = "1.0-SNAPSHOT"

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


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8-1.4.0-rc")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.0")
                implementation("io.ktor:ktor-client-core:1.3.2-1.4.0-rc")
                implementation("io.ktor:ktor-client-json:1.3.2-1.4.0-rc")
                implementation("io.ktor:ktor-client-serialization:1.3.2-1.4.0-rc")
                implementation("io.ktor:ktor-client-logging:1.3.2-1.4.0-rc")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:1.3.2-1.4.0-rc")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}