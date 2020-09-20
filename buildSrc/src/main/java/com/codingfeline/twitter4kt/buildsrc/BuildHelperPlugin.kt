package com.codingfeline.twitter4kt.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import java.io.File
import java.util.*

class BuildHelperPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.subprojects {
            if (path.endsWith("-api")) {
                afterEvaluate {
                    val task = createTestConfigTask()
                    // TODO depends on test tasks
                    tasks.findByName("compileKotlinJvm")?.dependsOn(task)
                }
            }
        }
    }

    private fun Project.getSecrets(): Properties {
        val secretsFile = rootDir.resolve("secrets.properties")
        if (!secretsFile.exists()) {
            throw IllegalStateException("secrets.properties does not exist")
        }
        val props = Properties()
        props.load(secretsFile.inputStream())
        return props
    }

    private fun Project.createTestConfigTask(): TaskProvider<Task> {
        val task = tasks.register("createTestConfig") {
            val secrets = getSecrets()
            val outputDir = File("${buildDir}/testconfig")
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
}