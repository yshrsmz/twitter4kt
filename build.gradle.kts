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
    gradleVersion = "6.5.1"
    distributionType = Wrapper.DistributionType.ALL
}