plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("dependencies")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(gradleKotlinDsl())
    implementation(PluginDeps.kotlin)
    implementation(PluginDeps.dokka)
    implementation(PluginDeps.mavenPublish)

//    implementation("com.codingfeline.twitter4kt.dependencies:dependencies:SNAPSHOT")
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
            implementationClass = "com.codingfeline.twitter4kt.buildhelper.BuildHelperPlugin"
        }
    }
}
