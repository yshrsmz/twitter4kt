plugins {
    id(Plugins.buildHelper)
    id(Plugins.versions) version Versions.versions
    id(Plugins.mavenPublish) version Versions.mavenPublish apply false
    id(Plugins.dokka) version Versions.kotlin apply false
    id(Plugins.multiplatform) version Versions.kotlin apply false
    id(Plugins.serialization) version Versions.kotlin apply false
}

val GROUP: String by project
val VERSION_NAME: String by project

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
//        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
}

val emptyProjects = listOf(":core", ":v1")
subprojects {
    if (!emptyProjects.contains(path)) {
        apply(plugin = Plugins.dokka)
        apply(plugin = Plugins.mavenPublish)

        tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {

        }
    }
}

tasks.wrapper {
    gradleVersion = Versions.gradle
    distributionType = Wrapper.DistributionType.ALL
}