plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    jcenter()
}

gradlePlugin {
    plugins {
        create("buildHelper") {
            id = "build-helper"
            implementationClass = "com.codingfeline.twitter4kt.buildsrc.BuildHelperPlugin"
        }
    }
}