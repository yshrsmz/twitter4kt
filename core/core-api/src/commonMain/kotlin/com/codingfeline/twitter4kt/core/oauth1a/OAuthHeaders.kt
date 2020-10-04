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
package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.getNonce
import com.codingfeline.twitter4kt.core.model.oauth1a.AccessToken
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.auth.AuthScheme
import io.ktor.http.auth.HeaderValueEncoding
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.util.AttributeKey
import kotlinx.datetime.Clock
import kotlin.collections.set

internal class OAuthFlowHeaders(
    internal val consumerKeys: ConsumerKeys,
    internal val oAuthConfig: OAuthConfig,
    internal val clock: Clock
) {

    internal class Configuration {
        internal lateinit var consumerKeys: ConsumerKeys
        internal lateinit var oAuthConfig: OAuthConfig
        internal lateinit var clock: Clock

        internal fun build(): OAuthFlowHeaders = OAuthFlowHeaders(consumerKeys, oAuthConfig, clock)
    }

    internal companion object Feature : HttpClientFeature<Configuration, OAuthFlowHeaders> {
        override val key: AttributeKey<OAuthFlowHeaders> = AttributeKey("OAuthFlowHeaders")

        @OptIn(ExperimentalStdlibApi::class)
        override fun install(feature: OAuthFlowHeaders, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                applyRequestHeaders(
                    request = context,
                    clock = feature.clock,
                    consumerKeys = feature.consumerKeys,
                    extraAuthHeaderValues = buildMap {
                        this["oauth_callback"] = feature.oAuthConfig.callback
                        if (feature.oAuthConfig.xAuthAccessType != null) {
                            this["x_auth_access_type"] = feature.oAuthConfig.xAuthAccessType.value
                        }
                    }
                )
            }
        }

        override fun prepare(block: Configuration.() -> Unit): OAuthFlowHeaders {
            return Configuration().apply(block).build()
        }
    }
}

internal class OAuthRequestHeaders(
    internal val consumerKeys: ConsumerKeys,
    internal val accessToken: AccessToken,
    internal val clock: Clock
) {
    internal class Configuration {
        internal lateinit var consumerKeys: ConsumerKeys
        internal lateinit var accessToken: AccessToken
        internal lateinit var clock: Clock

        internal fun build(): OAuthRequestHeaders = OAuthRequestHeaders(consumerKeys, accessToken, clock)
    }

    internal companion object Feature : HttpClientFeature<Configuration, OAuthRequestHeaders> {
        override val key: AttributeKey<OAuthRequestHeaders> = AttributeKey("OAuthRequestHeaders")

        override fun install(feature: OAuthRequestHeaders, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                applyRequestHeaders(
                    request = context,
                    clock = feature.clock,
                    consumerKeys = feature.consumerKeys,
                    accessToken = feature.accessToken
                )
            }
        }

        override fun prepare(block: Configuration.() -> Unit): OAuthRequestHeaders {
            return Configuration().apply(block).build()
        }
    }
}

internal fun initialBaseNonSignedOAuthHeaders(
    consumerKeys: ConsumerKeys,
    accessToken: AccessToken?,
    nonce: String,
    timestamp: String,
): MutableMap<String, String> {
    return mutableMapOf(
        "oauth_consumer_key" to consumerKeys.key,
        "oauth_nonce" to nonce,
        "oauth_signature_method" to "HMAC-SHA1",
        "oauth_timestamp" to timestamp,
        "oauth_version" to "1.0",
    ).apply {
        if (accessToken != null) {
            this["oauth_token"] = accessToken.token
        }
    }
}

internal fun applyRequestHeaders(
    request: HttpRequestBuilder,
    clock: Clock,
    consumerKeys: ConsumerKeys,
    accessToken: AccessToken? = null,
    extraAuthHeaderValues: Map<String, String> = emptyMap()
) {
    val timestamp = clock.now().epochSeconds
    val nonce = getNonce(timestamp)

    val authHeaderValues = initialBaseNonSignedOAuthHeaders(
        consumerKeys,
        accessToken,
        nonce,
        timestamp.toString()
    )
    authHeaderValues.putAll(extraAuthHeaderValues)

    val signature = createSignature(
        request.method,
        request.url.build(),
        authHeaderValues,
        request.body,
        consumerKeys,
        accessToken,
    )

    authHeaderValues["oauth_signature"] = signature

    val auth = HttpAuthHeader.Parameterized(
        AuthScheme.OAuth,
        authHeaderValues,
        HeaderValueEncoding.QUOTED_ALWAYS
    )

    request.headers {
        append(HttpHeaders.Authorization, auth.render())
    }
}
