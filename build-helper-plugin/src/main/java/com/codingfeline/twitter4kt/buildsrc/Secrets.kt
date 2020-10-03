package com.codingfeline.twitter4kt.buildsrc

import org.gradle.api.Project
import java.util.*

internal fun Project.loadSecrets(): Properties {
    val secretsFile = rootDir.resolve("secrets.properties")
    if (!secretsFile.exists()) {
        throw IllegalStateException("secrets.properties does not exist")
    }
    val props = Properties()
    props.load(secretsFile.inputStream())
    return props
}
