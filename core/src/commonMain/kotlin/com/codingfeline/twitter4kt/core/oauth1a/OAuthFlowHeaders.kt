package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.getNonce
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.auth.AuthScheme
import io.ktor.http.auth.HeaderValueEncoding
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.http.encodeOAuth
import io.ktor.http.parametersOf
import io.ktor.http.quote
import io.ktor.util.AttributeKey
import io.ktor.util.InternalAPI
import kotlinx.datetime.Clock

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

        override fun install(feature: OAuthFlowHeaders, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                applyRequestHeaders(context, feature.consumerKeys, feature.oAuthConfig, feature.clock)
            }
        }

        override fun prepare(block: Configuration.() -> Unit): OAuthFlowHeaders {
            return Configuration().apply(block).build()
        }

        @OptIn(InternalAPI::class)
        private fun applyRequestHeaders(
            request: HttpRequestBuilder,
            consumerKeys: ConsumerKeys,
            oAuthConfig: OAuthConfig,
            clock: Clock
        ) {
            val timestamp = clock.now().epochSeconds
            val nonce = getNonce(timestamp)

            val headerValues = initialNonSignedOAuthFlowHeaders(consumerKeys, oAuthConfig, nonce, timestamp.toString())

            val signature = createSignature(
                request.method,
                request.url.build(),
                headerValues,
                request.body,
                consumerKeys,
            )

            headerValues["oauth_signature"] = signature

            val auth = HttpAuthHeader.Parameterized(AuthScheme.OAuth, headerValues, HeaderValueEncoding.QUOTED_ALWAYS)
            val rendered = auth.parameters.sortedBy { it.name }
                .joinToString(",", prefix = "${auth.authScheme} ") { "${it.name}=${it.value.encodeOAuth().quote()}" }
            request.headers {
                append(HttpHeaders.Authorization, rendered)
//                append(
//                    HttpHeaders.UserAgent,
//                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36"
//                )
//                append(HttpHeaders.AcceptEncoding, "gzip")
//                append(HttpHeaders.AcceptLanguage, "ja-JP,ja;q=0.9,en-US;q=0.8,en;q=0.7")
            }
        }

        private fun createSignature(
            method: HttpMethod,
            url: Url,
            headers: Map<String, String>,
            requestBody: Any,
            consumerKeys: ConsumerKeys
        ): String {
            val oauthParameters = headers.mapValues { setOf(it.value) }.toMutableMap()
            url.parameters.forEach { key, values ->
                val current = oauthParameters.getOrElse(key) { setOf() }
                oauthParameters[key] = current + values.toSet()
            }

            if (requestBody is FormDataContent) {
                requestBody.formData.forEach { key, values ->
                    val current = oauthParameters.getOrElse(key) { setOf() }
                    oauthParameters[key] = current + values.toSet()
                }
            }

            val oauthParametersString = oauthParameters.entries
                .flatMap { (key, values) -> values.map { value -> "$key=$value".encodeOAuth() } }
                .sorted()
                .joinToString("&").encodeOAuth()

            val requestUrl = url.copy(parameters = parametersOf()).toString().encodeOAuth()

            val baseString = listOf(method.value, requestUrl, oauthParametersString).joinToString("&")
            println("createSignature,baseString: $baseString")
            val signingKey = "${consumerKeys.secret.encodeOAuth()}&"
            return hmacSha1(signingKey, baseString).encodeOAuth()
        }
    }
}