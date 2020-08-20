package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.Urls
import io.ktor.client.HttpClient
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import kotlinx.datetime.Clock

class OAuth1aFlow(
    private val consumerKeys: ConsumerKeys,
    private val oAuthConfig: OAuthConfig = OAuthConfig()
) {
    private val httpClient = HttpClient {
        // TODO: install request signer
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
        val url = URLBuilder(Urls.API_ENDPOINT)
            .path("oauth/request_token")
            .run {
//                parameters.apply {
//                    append("oauth_callback", oAuthConfig.callback)
//                }
                build()
            }

        val result = httpClient.post<String>(url = url) {
//            body = FormDataContent(parametersOf("oauth_callback" to listOf(oAuthConfig.callback)))
            body = FormDataContent(Parameters.Empty)
        }

        println("oauth/request_token: $result")

        return RequestToken("", "")
    }
}