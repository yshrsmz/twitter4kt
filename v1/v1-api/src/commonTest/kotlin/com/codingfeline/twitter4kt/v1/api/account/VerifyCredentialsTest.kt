package com.codingfeline.twitter4kt.v1.api.account

import com.codingfeline.twitter4kt.TEST_ACCESS_TOKEN
import com.codingfeline.twitter4kt.TEST_ACCESS_TOKEN_SECRET
import com.codingfeline.twitter4kt.TEST_CONSUMER_KEY
import com.codingfeline.twitter4kt.TEST_CONSUMER_SECRET
import com.codingfeline.twitter4kt.TEST_USER_ID
import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.Twitter
import com.codingfeline.twitter4kt.core.model.oauth1a.AccessToken
import com.codingfeline.twitter4kt.core.startSession
import com.codingfeline.twitter4kt.v1.api.runTest
import kotlin.test.Test

class VerifyCredentialsTest {

    private val consumerKeys = ConsumerKeys(
        key = TEST_CONSUMER_KEY,
        secret = TEST_CONSUMER_SECRET,
    )

    private val accessToken = AccessToken(
        token = TEST_ACCESS_TOKEN,
        secret = TEST_ACCESS_TOKEN_SECRET,
        userId = TEST_USER_ID,
        screenName = TEST_USER_ID
    )

    @Test
    fun test() = runTest {
        val twitter = Twitter {
            this.consumerKeys = this@VerifyCredentialsTest.consumerKeys
        }

        val apiClient = twitter.startSession(accessToken)
        val result = apiClient.account.verifyCredentials()

        println("result: $result")
    }
}