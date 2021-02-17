plugins {
    id(Plugins.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Kotlin.coroutines)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Dependencies.Kotlin.Test.common))
                implementation(kotlin(Dependencies.Kotlin.Test.annotations))
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin(Dependencies.Kotlin.Test.junit))
            }
        }
    }
}
