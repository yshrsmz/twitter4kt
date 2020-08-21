package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.apiUrl


data class RequestToken(
    val token: String,
    val secret: String,
    val callbackConfirmed: Boolean
) {
    val authorizationUrl: String = apiUrl("oauth/request_token")
        .also {
            it.parameters["oauth_token"] = token
        }
        .buildString()
}