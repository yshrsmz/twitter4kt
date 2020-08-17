package com.codingfeline.twitter4kt.core.oauth

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.getNonce
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.auth.AuthScheme
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.http.encodeOAuth
import io.ktor.http.parametersOf
import io.ktor.util.AttributeKey
import kotlinx.datetime.Clock

class OAuthFlowHeaders(
    val consumerKeys: ConsumerKeys,
    val clock: Clock
) {

    companion object Feature : HttpClientFeature<OAuthFlowHeaders, OAuthFlowHeaders> {
        override val key: AttributeKey<OAuthFlowHeaders> = AttributeKey("OAuthFlowHeaders")

        override fun install(feature: OAuthFlowHeaders, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                applyRequestHeaders(context, feature.consumerKeys, feature.clock)
            }
        }

        override fun prepare(block: OAuthFlowHeaders.() -> Unit): OAuthFlowHeaders {
            TODO("Not yet implemented")
        }

        private fun applyRequestHeaders(request: HttpRequestBuilder, consumerKeys: ConsumerKeys, clock: Clock) {
            val timestamp = clock.now().epochSeconds
            val nonce = getNonce(timestamp)

            val headerValues = mutableMapOf<String, String>().apply {
                this["oauth_nonce"] = nonce
                this["oauth_callback"] = "oob" // FIXME
                this["oauth_signature_method"] = "HMAC-SHA1"
                this["oauth_timestamp"] = timestamp.toString()
                this["oauth_consumer_key"] = consumerKeys.apiKey
                this["oauth_version"] = "1.0"
            }

            val signature = createSignature(request.method, request.url.build(), headerValues)

            headerValues["oauth_signature"] = signature

            val auth = HttpAuthHeader.Parameterized(AuthScheme.OAuth, headerValues)

            request.headers {
                append(HttpHeaders.Authorization, auth.render())
            }
        }

        private fun createSignature(method: HttpMethod, url: Url, headers: Map<String, String>): String {
            val oauthParameters = headers.mapValues { listOf(it.value) }.toMutableMap()
            url.parameters.forEach { key, values ->
                val current = oauthParameters.getOrElse(key) { listOf() }
                oauthParameters[key] = current + values
            }
            // TODO: concat POST request body

            oauthParameters.entries
                .flatMap { (key, values) -> values.map { value -> "${key}=${value.encodeOAuth()}" } }
                .sorted()
                .joinToString("&")

            val requestUrl = url.copy(parameters = parametersOf()).toString().encodeOAuth()

            val baseString = listOf(method.value, requestUrl, oauthParameters).joinToString("&")
            TODO()
        }
    }
}