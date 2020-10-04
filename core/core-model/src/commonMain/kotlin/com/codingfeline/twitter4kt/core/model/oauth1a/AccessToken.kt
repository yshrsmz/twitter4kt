package com.codingfeline.twitter4kt.core.model.oauth1a

public data class AccessToken(
    val token: String,
    val secret: String,
    val userId: String,
    val screenName: String,
)
