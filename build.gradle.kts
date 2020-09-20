plugins {
    id("com.github.ben-manes.versions").version("0.33.0")
    id("build-helper")
    kotlin("multiplatform") version "1.4.10" apply false
    kotlin("plugin.serialization") version "1.4.10" apply false
}

val GROUP: String by project
val VERSION_NAME: String by project

allprojects {

    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }

    group = GROUP
    version = VERSION_NAME
}

tasks.wrapper {
    gradleVersion = "6.6.1"
    distributionType = Wrapper.DistributionType.ALL
}