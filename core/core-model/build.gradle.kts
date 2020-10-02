plugins {
    id(Plugins.multiplatform)
    id(Plugins.serialization)
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
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.serialization)
            }

        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Deps.Kotlin.Test.common))
                implementation(kotlin(Deps.Kotlin.Test.annotations))
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin(Deps.Kotlin.Test.junit))
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
