package com.codingfeline.twitter4kt.v1.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExtendedEntities(
    @SerialName("media") val media: List<Entities.MediaEntity> = emptyList()
)