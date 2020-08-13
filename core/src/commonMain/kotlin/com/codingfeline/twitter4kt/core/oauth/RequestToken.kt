package com.codingfeline.twitter4kt.core.oauth

import io.ktor.http.Url
import io.ktor.http.parametersOf


data class RequestToken(
    val token: String,
    val tokenSecret: String
) {
    val authorizationUrl: String = Url("https://api.twitter.com/oauth/request_token")
        .copy(parameters = parametersOf("oauth_token" to listOf(token)))
        .toString()
}