package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.getNonce
import com.codingfeline.twitter4kt.model.oauth1a.AccessToken
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

class OAuthFlowHeaders(
    val consumerKeys: ConsumerKeys,
    val oAuthConfig: OAuthConfig,
    val clock: Clock
) {

    class Configuration {
        lateinit var consumerKeys: ConsumerKeys
        lateinit var oAuthConfig: OAuthConfig
        lateinit var clock: Clock

        internal fun build(): OAuthFlowHeaders = OAuthFlowHeaders(consumerKeys, oAuthConfig, clock)
    }

    companion object Feature : HttpClientFeature<Configuration, OAuthFlowHeaders> {
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
                    })
            }
        }

        override fun prepare(block: Configuration.() -> Unit): OAuthFlowHeaders {
            return Configuration().apply(block).build()
        }
    }
}

class OAuthRequestHeaders(
    val consumerKeys: ConsumerKeys,
    val accessToken: AccessToken,
    val clock: Clock
) {
    class Configuration {
        lateinit var consumerKeys: ConsumerKeys
        lateinit var accessToken: AccessToken
        lateinit var clock: Clock

        internal fun build(): OAuthRequestHeaders = OAuthRequestHeaders(consumerKeys, accessToken, clock)
    }

    companion object Feature : HttpClientFeature<Configuration, OAuthRequestHeaders> {
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
            TODO("Not yet implemented")
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