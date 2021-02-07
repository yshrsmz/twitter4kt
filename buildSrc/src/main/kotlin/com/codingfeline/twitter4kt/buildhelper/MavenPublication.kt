/**
 * Copyright 2020 Shimizu Yasuhiro (yshrsmz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.codingfeline.twitter4kt.buildhelper

import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.MavenPublishPluginExtension
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaPlugin

internal fun Project.configureMavenPublications() {
    pluginManager.apply {
        apply(MavenPublishPlugin::class.java)
        apply(DokkaPlugin::class.java)
    }
    val extension = extensions.getByType(MavenPublishPluginExtension::class.java)

    extension.releaseSigningEnabled = getGpgKey().isNotEmpty() && getGpgKeyPassword().isNotEmpty()

    extension.targets.apply {
        maybeCreate("testMaven").apply {
            releaseRepositoryUrl = file("${rootProject.buildDir}/localMaven").toURI().toString()
            snapshotRepositoryUrl = file("${rootProject.buildDir}/localMaven").toURI().toString()
            repositoryUsername = null
            repositoryPassword = null
        }
    }

    val signingExtension = extensions.getByType(SigningExtension::class.java)
    signingExtension.apply {
        val signingKey = getGpgKey()
        val password = getGpgKeyPassword()

        if (signingKey.isNotEmpty() && password.isNotEmpty()) {
            useInMemoryPgpKeys(signingKey, password)
        }
    }
}

fun Project.getGpgKey(): String {
    return findProperty("signingKey") as String? ?: ""
}

fun Project.getGpgKeyPassword(): String {
    return findProperty("signingKeyPassword") as String? ?: ""
}
