pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}
rootProject.name = "twitter4kt"

include(
    ":core:core-model",
    ":core:core-api",
    ":v1:v1-api",
    ":v1:v1-model"
)
