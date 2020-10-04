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
                implementation(Deps.Kotlin.serialization)
                implementation(Deps.Kotlin.coroutines)
                implementation(Deps.Kotlin.datetime)
                implementation(Deps.Ktor.core)
                implementation(Deps.Ktor.json)
                implementation(Deps.Ktor.serialization)

                api(project(Deps.Twitter4kt.coreApi))
                api(project(Deps.Twitter4kt.v1Model))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Deps.Kotlin.Test.common))
                implementation(kotlin(Deps.Kotlin.Test.annotations))

                implementation(Deps.Ktor.logging)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Deps.Ktor.okhttp)
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
