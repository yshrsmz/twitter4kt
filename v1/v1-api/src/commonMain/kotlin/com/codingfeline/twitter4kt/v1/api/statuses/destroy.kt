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
package com.codingfeline.twitter4kt.v1.api.statuses

import com.codingfeline.twitter4kt.core.ApiResult
import com.codingfeline.twitter4kt.core.apiUrl
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import com.codingfeline.twitter4kt.core.util.appendNotNulls
import com.codingfeline.twitter4kt.v1.api.postInternal
import com.codingfeline.twitter4kt.v1.model.status.Tweet
import io.ktor.http.Parameters

/**
 * Destroys an authenticating user's tweet.
 *
 * This can only be used on tweets that the user has authored. If successful it returns the deleted tweet data.
 *
 * This is rate limited by Twitter.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/tweets/post-and-engage/api-reference/post-statuses-destroy-id)
 *
 * @param id The ID of the tweet authenticated user's tweet that should be deleted
 */
@OptIn(Twitter4ktInternalAPI::class)
public suspend fun StatusesApi.destroy(
    id: Long
): ApiResult<Tweet> {
    val url = apiUrl("1.1/statuses/$id.json").build()
    return apiClient.postInternal<Tweet>(url, Parameters.build { })
}
