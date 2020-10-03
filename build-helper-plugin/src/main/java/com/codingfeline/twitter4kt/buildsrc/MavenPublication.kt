package com.codingfeline.twitter4kt.buildsrc

import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.MavenPublishPluginExtension
import org.gradle.api.Project
import org.jetbrains.dokka.gradle.DokkaPlugin
import java.util.*

internal fun Project.configureMavenPublications(secrets: Properties) {
    pluginManager.apply {
        apply(MavenPublishPlugin::class.java)
        apply(DokkaPlugin::class.java)
    }
    val extension = extensions.getByType(MavenPublishPluginExtension::class.java)
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
