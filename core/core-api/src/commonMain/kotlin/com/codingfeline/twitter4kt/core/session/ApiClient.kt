package com.codingfeline.twitter4kt.core.session

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.model.oauth1a.AccessToken
import com.codingfeline.twitter4kt.core.oauth1a.OAuthRequestHeaders
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import kotlinx.datetime.Clock

class ApiClient(
    private val consumerKeys: ConsumerKeys,
    private val accessToken: AccessToken,
    private val clock: Clock,
    private val httpClientConfig: HttpClientConfig<*>.() -> Unit = { /* no-op */ }
) {
    val httpClient: HttpClient by lazy {
        HttpClient {
            install(OAuthRequestHeaders) {
                this.consumerKeys = this@ApiClient.consumerKeys
                this.accessToken = this@ApiClient.accessToken
                this.clock = this@ApiClient.clock
            }

            this.apply(httpClientConfig)
        }
    }
}