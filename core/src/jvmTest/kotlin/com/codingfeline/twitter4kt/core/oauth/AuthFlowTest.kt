package com.codingfeline.twitter4kt.core.oauth

import com.codingfeline.twitter4kt.core.ConsumerKeys
import io.ktor.client.features.ClientRequestException
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AuthFlowTest {

    private val consumerKeys = ConsumerKeys(
        apiKey = "SsQ2VDwWWulvoD1TANfTa1kiP",
        apiKeySecret = "D5m8F2q1Xqk5X6ZJCiNigY1eLadNNcrLGGiQNXFwiOrfK7JhX6"
    )

    @Test
    fun test() = runBlocking {
        val flow = AuthFlow(consumerKeys)

        try {
            val requestToken = flow.fetchRequestToken()
            println(requestToken)
        } catch (e: ClientRequestException) {
            val body = e.response.content.readUTF8Line()
            println(e)
            println(body)
        }
    }
}