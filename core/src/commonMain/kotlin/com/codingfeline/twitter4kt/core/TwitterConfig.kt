package com.codingfeline.twitter4kt.core

import com.codingfeline.twitter4kt.core.oauth1a.OAuthConfig
import io.ktor.client.HttpClientConfig

class TwitterConfig(
    val consumerKeys: ConsumerKeys,
    val httpClientConfig: HttpClientConfig<*>.() -> Unit
) {
    companion object {}

    class Builder {
        var consumerKeys: ConsumerKeys? = null
        var httpClientConfig: HttpClientConfig<*>.() -> Unit = { /* no-op */ }
        var oAuthConfig: OAuthConfig? = null

        fun build(): TwitterConfig {
            val consumerKeys = requireNotNull(this.consumerKeys) { "ConsumerKeys must be provideD" }

            return TwitterConfig(
                consumerKeys = consumerKeys,
                httpClientConfig = httpClientConfig
            )
        }
    }
}