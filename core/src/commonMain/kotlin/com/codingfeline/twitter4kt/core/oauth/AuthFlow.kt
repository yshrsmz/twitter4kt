package com.codingfeline.twitter4kt.core.oauth

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.Urls
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.URLBuilder
import io.ktor.util.Digest

class AuthFlow(
    private val consumerKeys: ConsumerKeys,
    private val oAuthConfig: OAuthConfig = OAuthConfig()
) {
    private val httpClient = HttpClient {
        // TODO: install request signer
    }

    suspend fun fetchRequestToken(): RequestToken {
        Digest()
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