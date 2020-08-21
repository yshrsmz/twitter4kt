package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.apiUrl
import io.ktor.client.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.datetime.Clock

class OAuth1aFlow(
    private val consumerKeys: ConsumerKeys,
    private val oAuthConfig: OAuthConfig = OAuthConfig()
) {
    private val httpClient = HttpClient {
        install(OAuthFlowHeaders.Feature) {
            this.consumerKeys = this@OAuth1aFlow.consumerKeys
            this.oAuthConfig = this@OAuth1aFlow.oAuthConfig
            this.clock = Clock.System
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    suspend fun fetchRequestToken(): RequestToken {
        val url = apiUrl("oauth/request_token").build()
        val res = httpClient.post<String>(url = url) {
            body = FormDataContent(Parameters.Empty)
        }

        val results = res.split("&")
            .map { params ->
                params.split("=", limit = 2)
                    .let { it.first() to it.last() }
            }
            .toMap()

        val token = requireNotNull(results["oauth_token"]) { "oauth_token is missing" }
        val secret = requireNotNull(results["oauth_token_secret"]) { "oauth_token_secret is missing" }
        val callbackConfirmed =
            requireNotNull(results["oauth_callback_confirmed"]) { "oauth_callback_confirmed is missing" }.toBoolean()

        return RequestToken(
            token,
            secret,
            callbackConfirmed
        )
    }
}