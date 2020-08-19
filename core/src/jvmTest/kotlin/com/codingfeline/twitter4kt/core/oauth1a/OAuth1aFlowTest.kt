package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.TEST_CONSUMER_KEY
import com.codingfeline.twitter4kt.TEST_CONSUMER_SECRET
import com.codingfeline.twitter4kt.core.ConsumerKeys
import io.ktor.client.features.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class OAuth1aFlowTest {

    private val consumerKeys = ConsumerKeys(
        apiKey = TEST_CONSUMER_KEY,
        apiKeySecret = TEST_CONSUMER_SECRET,
    )

    @Test
    fun test() = runBlocking {
        val flow = OAuth1aFlow(consumerKeys)

        try {
            val requestToken = flow.fetchRequestToken()
            println(requestToken)
        } catch (e: ClientRequestException) {
            val body = e.response?.content?.readUTF8Line() ?: ""
            println(e)
            println(body)
        }
    }
}