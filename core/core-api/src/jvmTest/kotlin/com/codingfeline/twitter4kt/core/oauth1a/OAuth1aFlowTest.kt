package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.TEST_CONSUMER_KEY
import com.codingfeline.twitter4kt.TEST_CONSUMER_SECRET
import com.codingfeline.twitter4kt.core.ConsumerKeys
import io.ktor.client.features.ClientRequestException
import io.ktor.utils.io.readUTF8Line
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
        val flow = OAuth1aFlow(consumerKeys, OAuthConfig(callback = "oob"))

        try {
            val requestToken = flow.fetchRequestToken()
            println(requestToken)
            println(requestToken.authorizationUrl())
        } catch (e: ClientRequestException) {
            val body = e.response.content.readUTF8Line() ?: ""
            println(e)
            println(body)
        }
    }

    @Ignore
    @Test
    fun accessToken() = runBlocking {
        val flow = OAuth1aFlow(consumerKeys, OAuthConfig(callback = "oob"))

        try {
            val accessToken = flow.fetchAccessToken("ENTER_YOUR_OAUTH_TOKEN", "ENTER_YOUR_OAUTH_VERIFIER")
            println(accessToken)
        } catch (e: ClientRequestException) {
            val body = e.response.content.readUTF8Line() ?: ""
            println(e)
            println(body)
        }
    }
}