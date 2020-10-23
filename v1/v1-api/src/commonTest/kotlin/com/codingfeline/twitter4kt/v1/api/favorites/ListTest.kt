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
package com.codingfeline.twitter4kt.v1.api.favorites

import com.codingfeline.twitter4kt.TEST_ACCESS_TOKEN
import com.codingfeline.twitter4kt.TEST_ACCESS_TOKEN_SECRET
import com.codingfeline.twitter4kt.TEST_CONSUMER_KEY
import com.codingfeline.twitter4kt.TEST_CONSUMER_SECRET
import com.codingfeline.twitter4kt.TEST_SCREEN_NAME
import com.codingfeline.twitter4kt.TEST_USER_ID
import com.codingfeline.twitter4kt.core.ConsumerKeys
import com.codingfeline.twitter4kt.core.Twitter
import com.codingfeline.twitter4kt.core.isFailure
import com.codingfeline.twitter4kt.core.model.oauth1a.AccessToken
import com.codingfeline.twitter4kt.core.startSession
import com.codingfeline.twitter4kt.v1.api.runTest
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.utils.io.readUTF8Line
import kotlin.test.Ignore
import kotlin.test.Test

class ListTest {
    private val consumerKeys = ConsumerKeys(
        key = TEST_CONSUMER_KEY,
        secret = TEST_CONSUMER_SECRET,
    )

    private val accessToken = AccessToken(
        token = TEST_ACCESS_TOKEN,
        secret = TEST_ACCESS_TOKEN_SECRET,
        userId = TEST_USER_ID,
        screenName = TEST_SCREEN_NAME,
    )

    @Ignore
    @Test
    fun test() = runTest {
        val twitter = Twitter {
            this.consumerKeys = this@ListTest.consumerKeys
            this.httpClientConfig = {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }
        }

        val apiClient = twitter.startSession(accessToken)
        val result = apiClient.favorites.list()

        println("result: $result")
        if (result.isFailure()) {
            val error = result.error
            if (error is ClientRequestException) {
                val res = error.response.content.readUTF8Line()
                println("res: $res")
            }
        }
    }
}
