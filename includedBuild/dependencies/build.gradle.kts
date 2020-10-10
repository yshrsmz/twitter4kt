plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "com.codingfeline.twitter4kt.dependencies"
version = "SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(gradleKotlinDsl())
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
        create("dependencies") {
            id = "dependencies"
            implementationClass = "com.codingfeline.twitter4kt.dependencies.DependenciesPlugin"
        }
    }
}
