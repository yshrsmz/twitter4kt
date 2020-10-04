/**
 * Copyright 2020 Shimizu Yasuhiro (yshrsmz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
