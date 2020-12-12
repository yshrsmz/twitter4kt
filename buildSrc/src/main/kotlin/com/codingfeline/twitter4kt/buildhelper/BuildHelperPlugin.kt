package com.codingfeline.twitter4kt.buildhelper

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.withConvention
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.util.Properties

private const val TESTCONFIG_DIR = "testconfig"

class BuildHelperPlugin : Plugin<Project> {
    private val ignoreModules = listOf(":core", ":v1")

    private var _secrets: Properties? = null

    private val Project.secrets: Properties
        get() = _secrets ?: kotlin.run { loadSecrets().also { _secrets = it } }

    override fun apply(project: Project) {
        project.subprojects {
            if (ignoreModules.contains(path)) return@subprojects

            afterEvaluate {
                configureKotlin()
                configureApiModules()
            }
            configureMavenPublications()
        }
    }

    private fun Project.configureKotlin() {
        tasks.withType<KotlinCompile>().all {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            kotlinOptions.jvmTarget = "1.8"
        }
        withConvention(JavaPluginConvention::class) {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinMultiplatformExtension.apply {
            explicitApi()
            sourceSets.all {
                languageSettings
                    .useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
            targets.all {
                when (platformType) {
                    KotlinPlatformType.jvm -> {
                        (this as KotlinJvmTarget).compilations.all {
                            compileKotlinTask.sourceCompatibility = "1.8"
                            compileKotlinTask.targetCompatibility = "1.8"
                            kotlinOptions.jvmTarget = "1.8"
                        }
                    }
                    else -> {
                        // no-op
                    }
                }
            }
        }
    }

    private fun Project.configureApiModules() {
        if (path.endsWith("-api")) {
            setupTestConfig()
        }
    }

    private fun Project.setupTestConfig() {
        val task = createTestConfigTask()
        // TODO depends on test tasks
        tasks.named("compileTestKotlinJvm") {
            dependsOn(task)
        }

        kotlinMultiplatformExtension.sourceSets.getByName("commonTest")
            .kotlin.srcDir("$buildDir/$TESTCONFIG_DIR")
    }

    private fun Project.createTestConfigTask(): TaskProvider<Task> {
        val task = tasks.register("createTestConfig") {
            val outputDir = File("$buildDir/$TESTCONFIG_DIR")
            val consumerKey = secrets["twitter_consumer_key"]
            val consumerSecret = secrets["twitter_consumer_secret"]
            val accessToken = secrets["twitter_access_token"]
            val accessTokenSecret = secrets["twitter_access_token_secret"]
            val userId = secrets["twitter_user_id"]
            val screenName = secrets["twitter_screen_name"]

            inputs.property("consumerKey", consumerKey)
            inputs.property("consumerSecret", consumerSecret)
            inputs.property("accessToken", accessToken)
            inputs.property("accessTokenSecret", accessTokenSecret)
            inputs.property("userId", userId)
            inputs.property("screenName", screenName)
            outputs.dir(outputDir)
            group = "twitter4kt"

            doLast {
                val configFile = file("$outputDir/com/codingfeline/twitter4kt/TestConfig.kt")
                if (configFile.exists()) configFile.delete()

                configFile.parentFile.mkdirs()
                configFile.writeText(
                    """// Generated file. Do not edit!
                        |package com.codingfeline.twitter4kt
                        |
                        |val TEST_CONSUMER_KEY = "$consumerKey"
                        |val TEST_CONSUMER_SECRET = "$consumerSecret"
                        |val TEST_ACCESS_TOKEN = "$accessToken"
                        |val TEST_ACCESS_TOKEN_SECRET = "$accessTokenSecret"
                        |val TEST_USER_ID = "$userId"
                        |val TEST_SCREEN_NAME = "$screenName"
                    """.trimMargin()
                )
            }
        }
        return task
    }

    private val Project.kotlinMultiplatformExtension: KotlinMultiplatformExtension
        get() = requireNotNull(extensions.getByType(KotlinMultiplatformExtension::class.java))
}
