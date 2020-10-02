plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("java-gradle-plugin")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
}

gradlePlugin {
    plugins {
        create("buildHelper") {
            id = "build-helper"
            implementationClass = "com.codingfeline.twitter4kt.buildsrc.BuildHelperPlugin"
        }
    }
}