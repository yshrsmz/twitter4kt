package com.codingfeline.twitter4kt.model.oauth1a

data class AccessToken(
    val token: String,
    val secret: String,
    val userId: String,
    val screenName: String,
)