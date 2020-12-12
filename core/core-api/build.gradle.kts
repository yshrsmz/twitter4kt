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
                implementation(KotlinDeps.serialization)
                implementation(KotlinDeps.coroutines)
                implementation(KotlinDeps.datetime)
                implementation(KtorDeps.core)
                implementation(KtorDeps.json)
                implementation(KtorDeps.serialization)

                api(project(Twitter4ktDeps.coreModel))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(KotlinTestDeps.common))
                implementation(kotlin(KotlinTestDeps.annotations))

                implementation(KtorDeps.logging)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(KtorDeps.okhttp)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin(KotlinTestDeps.junit))
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
