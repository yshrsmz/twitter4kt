package com.codingfeline.twitter4kt.core

import com.codingfeline.twitter4kt.core.oauth1a.OAuthConfig
import io.ktor.client.HttpClientConfig
import kotlinx.datetime.Clock

public class TwitterConfig(
    public val consumerKeys: ConsumerKeys,
    public val httpClientConfig: HttpClientConfig<*>.() -> Unit,
    public val clock: Clock
) {
    public companion object {}

    public class Builder {
        public var consumerKeys: ConsumerKeys? = null
        public var httpClientConfig: HttpClientConfig<*>.() -> Unit = { /* no-op */ }
        public var oAuthConfig: OAuthConfig? = null
        public var clock: Clock = Clock.System

        public fun build(): TwitterConfig {
            val consumerKeys = requireNotNull(this.consumerKeys) { "ConsumerKeys must be provideD" }

            return TwitterConfig(
                consumerKeys = consumerKeys,
                httpClientConfig = httpClientConfig,
                clock = clock
            )
        }
    }
}
