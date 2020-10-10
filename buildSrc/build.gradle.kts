plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    api("com.codingfeline.twitter4kt.dependencies:dependencies:SNAPSHOT")
}
