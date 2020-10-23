package com.codingfeline.twitter4kt.v1.api.favorites

import com.codingfeline.twitter4kt.core.ApiResult
import com.codingfeline.twitter4kt.core.apiUrl
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import com.codingfeline.twitter4kt.core.util.appendNotNulls
import com.codingfeline.twitter4kt.v1.api.getInternal
import com.codingfeline.twitter4kt.v1.model.status.Tweet
import io.ktor.http.Parameters

/**
 * Get's a non-protected user's timeline of their likes (previously favorites). This can be requested either using their Twitter ID
 * or by using their screen name. One must be passed in for the call to succeed. This will return the full 280 characters
 * of a tweet in the fullText field rather than the text field. If no userId or screenName is provided it returns the
 * authenticated user's likes.
 *
 * This is rate limited both at the user and application-wide level
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/tweets/post-and-engage/api-reference/get-favorites-list)
 *
 * @param userId The ID of the user for whom to return results.
 * @param screenName The screen name of the user for whom to return results.
 * @param count Specifies the number of records to retrieve. Must be less than or equal to 200. Defaults to 20. The value of count is best thought of as a limit to the number of tweets to return because suspended or deleted content is removed after the count has been applied.
 * @param sinceId Returns results with an ID greater than (that is, more recent than) the specified ID. There are limits to the number of Tweets which can be accessed through the API. If the limit of Tweets has occured since the since_id, the since_id will be forced to the oldest ID available.
 * @param maxId Returns results with an ID less than (that is, older than) or equal to the specified ID.
 * @param includeEntities The entities node will not be included when set to false.
 */
@OptIn(Twitter4ktInternalAPI::class)
public suspend fun FavoritesApi.list(
    userId: String? = null,
    screenName: String? = null,
    count: Int? = null,
    sinceId: Long? = null,
    maxId: Long? = null,
    includeEntities: Boolean? = null,
): ApiResult<List<Tweet>> {
    val parameters = Parameters.build {
        appendNotNulls(
            "user_id" to userId,
            "screen_name" to screenName,
            "count" to count,
            "since_id" to sinceId,
            "max_id" to maxId,
            "include_entities" to includeEntities,
            "tweet_mode" to "extended"
        )
    }
    val url = apiUrl("1.1/favorites/list.json", parameters = parameters)
    return apiClient.getInternal(url.build())
}
