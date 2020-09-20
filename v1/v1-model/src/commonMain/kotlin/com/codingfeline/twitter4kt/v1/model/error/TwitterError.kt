package com.codingfeline.twitter4kt.v1.model.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitterError(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String
)