plugins {
    id(Plugins.multiplatform)
}

kotlin {
    jvm()
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
                implementation(Dependencies.Kotlin.serialization)
                implementation(Dependencies.Kotlin.coroutines)
                implementation(Dependencies.Kotlin.datetime)
                implementation(Dependencies.Ktor.core)
                implementation(Dependencies.Ktor.json)
                implementation(Dependencies.Ktor.serialization)

                api(project(Dependencies.Twitter4kt.coreModel))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Dependencies.Kotlin.Test.common))
                implementation(kotlin(Dependencies.Kotlin.Test.annotations))

                implementation(Dependencies.Ktor.logging)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.okhttp)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin(Dependencies.Kotlin.Test.junit))
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
