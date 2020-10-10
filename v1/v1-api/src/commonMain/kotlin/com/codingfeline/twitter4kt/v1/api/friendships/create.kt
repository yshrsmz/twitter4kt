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
package com.codingfeline.twitter4kt.v1.api.friendships

import com.codingfeline.twitter4kt.core.ApiResult
import com.codingfeline.twitter4kt.core.apiUrl
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import com.codingfeline.twitter4kt.core.util.appendNotNulls
import com.codingfeline.twitter4kt.v1.api.postInternal
import com.codingfeline.twitter4kt.v1.model.user.User
import io.ktor.http.Parameters

/**
 * Allows the authenticating user to follow (friend) the user specified in the ID parameter.
 *
 * Returns the followed user when successful. Returns a string describing the failure condition when unsuccessful.
 * If the user is already friends with the user a HTTP 403 may be returned, though for performance reasons this method may also return a HTTP 200 OK message even if the follow relationship already exists.
 *
 * Actions taken in this method are asynchronous. Changes will be eventually consistent.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/accounts-and-users/follow-search-get-users/api-reference/post-friendships-create)
 *
 * @param screenName The screen name of the user to follow.
 * @param follow Enable notifications for the target user.
 * @return [com.codingfeline.twitter4kt.v1.model.user.User]
 */
public suspend fun FriendshipsApi.createByScreenName(
    screenName: String,
    follow: Boolean? = null
): ApiResult<User> = create(screenName = screenName, userId = null, follow = follow)

/**
 * Allows the authenticating user to follow (friend) the user specified in the ID parameter.
 *
 * Returns the followed user when successful. Returns a string describing the failure condition when unsuccessful.
 * If the user is already friends with the user a HTTP 403 may be returned, though for performance reasons this method may also return a HTTP 200 OK message even if the follow relationship already exists.
 *
 * Actions taken in this method are asynchronous. Changes will be eventually consistent.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/accounts-and-users/follow-search-get-users/api-reference/post-friendships-create)
 *
 * @param userId The screen name of the user to follow.
 * @param follow Enable notifications for the target user.
 * @return [com.codingfeline.twitter4kt.v1.model.user.User]
 */
public suspend fun FriendshipsApi.createByUserId(
    userId: String,
    follow: Boolean? = null
): ApiResult<User> = create(screenName = null, userId = userId, follow = follow)

/**
 * Allows the authenticating user to follow (friend) the user specified in the ID parameter.
 *
 * Returns the followed user when successful. Returns a string describing the failure condition when unsuccessful.
 * If the user is already friends with the user a HTTP 403 may be returned, though for performance reasons this method may also return a HTTP 200 OK message even if the follow relationship already exists.
 *
 * Actions taken in this method are asynchronous. Changes will be eventually consistent.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/accounts-and-users/follow-search-get-users/api-reference/post-friendships-create)
 *
 * @param screenName The screen name of the user to follow.
 * @param userId The screen name of the user to follow.
 * @param follow Enable notifications for the target user.
 * @return [com.codingfeline.twitter4kt.v1.model.user.User]
 */
@OptIn(Twitter4ktInternalAPI::class)
private suspend fun FriendshipsApi.create(
    screenName: String?,
    userId: String?,
    follow: Boolean? = null
): ApiResult<User> {
    val url = apiUrl("1.1/friendships/create.json").build()
    val body = Parameters.build {
        appendNotNulls(
            "screen_name" to screenName,
            "user_id" to userId,
            "follow" to follow
        )
    }
    return apiClient.postInternal(url, body)
}
