package com.codingfeline.twitter4kt.model.oauth1a

data class RequestToken(
    val token: String,
    val secret: String,
    val callbackConfirmed: Boolean
)