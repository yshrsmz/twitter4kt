package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.model.oauth1a.AccessToken
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.encodeOAuth
import io.ktor.http.parametersOf

internal fun createSignature(
    method: HttpMethod,
    url: Url,
    headers: Map<String, String>,
    requestBody: Any,
    consumerKeys: ConsumerKeys,
    accessToken: AccessToken? = null,
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
        .flatMap { (key, values) -> values.map { value -> "$key=$value" } }
        .sorted()
        .joinToString("&").encodeOAuth()

    val requestUrl = url.copy(parameters = parametersOf()).toString().encodeOAuth()

    val baseString = listOf(method.value, requestUrl, oauthParametersString).joinToString("&")
    val signingKey = "${consumerKeys.secret.encodeOAuth()}&${accessToken?.secret.orEmpty()}"

    return hmacSha1(signingKey, baseString)
}

expect fun hmacSha1(key: String, text: String): String