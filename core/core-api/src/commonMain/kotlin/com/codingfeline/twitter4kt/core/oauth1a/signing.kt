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
import com.codingfeline.twitter4kt.core.model.oauth1a.AccessToken
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.encodeOAuth
import io.ktor.http.encodeURLParameter
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
            oauthParameters[key] = current + values.map { it.encodeURLParameter() }.toSet()
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

internal expect fun hmacSha1(key: String, text: String): String
