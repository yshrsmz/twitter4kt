plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    implementation("com.vanniktech:gradle-maven-publish-plugin:0.13.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")
}

gradlePlugin {
    plugins {
        create("BuildHelper") {
            id = "build-helper"
            implementationClass = "com.codingfeline.twitter4kt.buildhelper.BuildHelperPlugin"
        }
    }
}
