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
import org.jetbrains.dokka.gradle.DokkaPlugin
import java.util.Properties

internal fun Project.configureMavenPublications(secrets: Properties) {
    pluginManager.apply {
        apply(MavenPublishPlugin::class.java)
        apply(DokkaPlugin::class.java)
    }
    val extension = extensions.getByType(MavenPublishPluginExtension::class.java)
    extension.releaseSigningEnabled = false
    extension.targets.apply {
        maybeCreate("uploadArchives").apply {
            releaseRepositoryUrl = bintrayReleaseRepositoryUrl
            repositoryUsername = secrets.getProperty("BINTRAY_USER")
            repositoryPassword = secrets.getProperty("BINTRAY_API_KEY")
        }
        maybeCreate("testMaven").apply {
            releaseRepositoryUrl = file("${rootProject.buildDir}/localMaven").toURI().toString()
            snapshotRepositoryUrl = file("${rootProject.buildDir}/localMaven").toURI().toString()
        }
    }
}

private val Project.bintrayReleaseRepositoryUrl: String
    get() {
        val bintrayOrg = requireNotNull(findProperty("BINTRAY_ORG") as String?) { "BINTRAY_ORG" }
        val bintrayRepository = requireNotNull(findProperty("BINTRAY_REPOSITORY") as String?) { "BINTRAY_REPOSITORY" }
        val pomArtifactId = requireNotNull(findProperty("POM_ARTIFACT_ID") as String?) { "POM_ARTIFACT_ID" }
        return "https://api.bintray.com/maven/$bintrayOrg/$bintrayRepository/$pomArtifactId"
    }
