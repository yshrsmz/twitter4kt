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
package com.codingfeline.twitter4kt.core.session

import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.model.oauth1a.AccessToken
import com.codingfeline.twitter4kt.core.oauth1a.OAuthRequestHeaders
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

public interface ApiClient {
    public val consumerKeys: ConsumerKeys
    public val accessToken: AccessToken
}

@Twitter4ktInternalAPI
public interface ExtendableApiClient : ApiClient {
    public val json: Json
    public val httpClient: HttpClient
}

@OptIn(Twitter4ktInternalAPI::class)
internal class ApiClientImpl constructor(
    override val consumerKeys: ConsumerKeys,
    override val accessToken: AccessToken,
    private val clock: Clock,
    private val httpClientConfig: HttpClientConfig<*>.() -> Unit = { /* no-op */ }
) : ExtendableApiClient {

    override val json: Json = Json {
        isLenient = false
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = false
    }

    override val httpClient: HttpClient by lazy {
        HttpClient {
            install(OAuthRequestHeaders) {
                this.consumerKeys = this@ApiClientImpl.consumerKeys
                this.accessToken = this@ApiClientImpl.accessToken
                this.clock = this@ApiClientImpl.clock
            }
            install(JsonFeature) {
                this.serializer = KotlinxSerializer(json = json)
            }

            this.apply(httpClientConfig)
        }
    }

    companion object
}

@Suppress("FunctionName")
internal fun ApiClient(
    consumerKeys: ConsumerKeys,
    accessToken: AccessToken,
    clock: Clock,
    httpClientConfig: HttpClientConfig<*>.() -> Unit = { /* no-op */ }
): ApiClient = ApiClientImpl(consumerKeys, accessToken, clock, httpClientConfig)
