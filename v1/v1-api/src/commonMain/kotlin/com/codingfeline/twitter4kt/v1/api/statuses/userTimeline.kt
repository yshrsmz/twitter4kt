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
import com.codingfeline.twitter4kt.v1.api.getInternal
import com.codingfeline.twitter4kt.v1.model.status.Tweet
import io.ktor.http.Parameters

/**
 * Get's a non-protected user's timeline of their tweets/retweets. This can be requested either using their Twitter ID
 * or by using their screen name. One must be passed in for the call to succeed. This will return the full 280 characters
 * of a tweet in the fullText field rather than the text field
 *
 * This is rate limited both at the user and application-wide level
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/tweets/timelines/api-reference/get-statuses-user_timeline)
 *
 * @param count Specifies the number of records to retrieve. Must be less than or equal to 200. Defaults to 20. The value of count is best thought of as a limit to the number of tweets to return because suspended or deleted content is removed after the count has been applied.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets which can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param excludeReplies This parameter will prevent replies from appearing in the returned timeline. Using exclude_replies with the count parameter will mean you will receive up-to count Tweets — this is because the count parameter retrieves that many Tweets before filtering out retweets and replies.
 * @param includeEntities The entities node will not be included when set to false.
 * @param includeRts When set to false , the timeline will strip any native retweets (though they will still count toward both the maximal length of the timeline and the slice selected by the count parameter). Note: If you're using the trim_user parameter in conjunction with include_rts, the retweets will still contain a full user object.
 */
@OptIn(Twitter4ktInternalAPI::class)
public suspend fun StatusesApi.userTimeline(
    userId: String? = null,
    screenName: String? = null,
    count: Int? = null,
    sinceId: String? = null,
    maxId: String? = null,
    excludeReplies: Boolean? = null,
    includeEntities: Boolean? = null,
    includeRts: Boolean? = null,
): ApiResult<List<Tweet>> {
    val parameters = Parameters.build {
        appendNotNulls(
            "user_id" to userId,
            "screen_name" to screenName,
            "count" to count,
            "since_id" to sinceId,
            "max_id" to maxId,
            "exclude_replies" to excludeReplies,
            "include_entities" to includeEntities,
            "include_rts" to includeRts,
        )
    }
    val url = apiUrl("1.1/statuses/user_timeline.json", parameters = parameters)
    return apiClient.getInternal(url.build())
}
