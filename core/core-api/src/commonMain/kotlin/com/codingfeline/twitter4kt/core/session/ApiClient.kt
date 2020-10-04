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

interface ApiClient {
    val consumerKeys: ConsumerKeys
    val accessToken: AccessToken
}

@Twitter4ktInternalAPI
interface ExtendableApiClient : ApiClient {
    val json: Json
    val httpClient: HttpClient
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
fun ApiClient(
    consumerKeys: ConsumerKeys,
    accessToken: AccessToken,
    clock: Clock,
    httpClientConfig: HttpClientConfig<*>.() -> Unit = { /* no-op */ }
): ApiClient = ApiClientImpl(consumerKeys, accessToken, clock, httpClientConfig)