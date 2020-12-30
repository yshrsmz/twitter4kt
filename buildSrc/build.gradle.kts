plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    implementation("com.vanniktech:gradle-maven-publish-plugin:${Versions.mavenPublish}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}")
}

gradlePlugin {
    plugins {
        create("BuildHelper") {
            id = "build-helper"
            implementationClass = "com.codingfeline.twitter4kt.buildhelper.BuildHelperPlugin"
        }
    }
}

kotlin {
    // Add Deps to compilation, so it will become available in main project
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}
