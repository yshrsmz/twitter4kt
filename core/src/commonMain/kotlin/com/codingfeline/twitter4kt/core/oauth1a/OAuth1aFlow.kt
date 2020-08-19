package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.Urls
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class OAuth1aFlow(
    private val consumerKeys: ConsumerKeys,
    private val oAuthConfig: OAuthConfig = OAuthConfig()
) {
    private val httpClient = HttpClient {
        // TODO: install request signer
    }

    suspend fun fetchRequestToken(): RequestToken {
        val url = URLBuilder(Urls.API_ENDPOINT)
            .path("oauth/request_token")
            .run {
                parameters.apply {
                    append("oauth_callback", oAuthConfig.callback)
                    append("oauth_consumer_key", consumerKeys.apiKey)
                }
                build()
            }

        val result = httpClient.post<String>(url = url)

        println("oauth/request_token: $result")

        return RequestToken("", "")
    }
}