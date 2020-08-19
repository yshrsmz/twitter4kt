package com.codingfeline.twitter4kt.core

import io.ktor.client.*

class TwitterConfig(
    var consumerKeys: ConsumerKeys,
    var httpClientConfig: HttpClientConfig<*>.() -> Unit
) {
    companion object {}

    class Builder {
        var consumerKeys: ConsumerKeys? = null
        var httpClientConfig: HttpClientConfig<*>.() -> Unit = { /* no-op */ }

        fun build(): TwitterConfig {
            val consumerKeys = requireNotNull(this.consumerKeys) { "ConsumerKeys must be provideD" }

            return TwitterConfig(
                consumerKeys = consumerKeys,
                httpClientConfig = httpClientConfig
            )
        }
    }
}