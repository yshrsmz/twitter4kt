plugins {
    id("com.github.ben-manes.versions").version("0.33.0")
    id("build-helper")
    id("com.vanniktech.maven.publish") version "0.13.0" apply false
    id("org.jetbrains.dokka") version "1.4.10" apply false
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
}

val emptyProjects = listOf(":core", ":v1")
subprojects {
    if (!emptyProjects.contains(path)) {
        apply(plugin = "org.jetbrains.dokka")
        apply(plugin = "com.vanniktech.maven.publish")

        tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {

        }
    }
}

tasks.wrapper {
    gradleVersion = "6.6.1"
    distributionType = Wrapper.DistributionType.ALL
}