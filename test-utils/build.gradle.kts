plugins {
    id(Plugins.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Kotlin.serialization)
                implementation(Dependencies.Kotlin.coroutines)
                implementation(Dependencies.Kotlin.datetime)
                implementation(Dependencies.Ktor.core)
                implementation(Dependencies.Ktor.json)
                implementation(Dependencies.Ktor.serialization)
                implementation(Dependencies.okio)

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
    }
}
