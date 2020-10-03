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
    compileOnly(gradleKotlinDsl())
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.4.10")
    implementation("com.vanniktech:gradle-maven-publish-plugin:0.13.0")
}

kotlin {
    target {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

gradlePlugin {
    plugins {
        create("buildHelper") {
            id = "build-helper"
            implementationClass = "com.codingfeline.twitter4kt.buildsrc.BuildHelperPlugin"
        }
    }
}
