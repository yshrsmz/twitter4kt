package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.TEST_CONSUMER_KEY
import com.codingfeline.twitter4kt.TEST_CONSUMER_SECRET
import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.model.fold
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test

class OAuth1aFlowTest {

    private val consumerKeys = ConsumerKeys(
        key = TEST_CONSUMER_KEY,
        secret = TEST_CONSUMER_SECRET,
    )

    @Ignore
    @Test
    fun requestToken() = runBlocking {
        val flow = OAuth1aFlow(
            consumerKeys = consumerKeys,
            oAuthConfig = OAuthConfig(callback = "oob"),
            httpClientConfig = {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            })

        val result = flow.fetchRequestToken()
        result.fold(
            onSuccess = { value ->
                println(value)
                println(value.authorizationUrl())
            },
            onFailure = { e ->
                println(e)
            }
        )
    }

    @Ignore
    @Test
    fun accessToken() = runBlocking {
        val flow = OAuth1aFlow(
            consumerKeys = consumerKeys,
            oAuthConfig = OAuthConfig(callback = "oob"),
            httpClientConfig = {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            })

        val result = flow.fetchAccessToken("ENTER_YOUR_OAUTH_TOKEN", "ENTER_YOUR_OAUTH_VERIFIER")
        result.fold(
            onSuccess = { value -> println(value) },
            onFailure = { e -> println(e.message) }
        )
    }
}
