package com.codingfeline.twitter4kt.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExtendedEntities(
    @SerialName("media") val media: List<Entities.MediaEntity>
)