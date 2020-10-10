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
 * Updates the authenticating user's current status, also known as Tweeting.
 *
 * For each update attempt, the update text is compared with the authenticating user's recent Tweets.
 * Any attempt that would result in duplication will be blocked, resulting in a 403 error.
 * A user cannot submit the same status twice in a row.
 *
 * While not rate limited by the API, a user is limited in the number of Tweets they can create at a time.
 * If the number of updates posted by the user reaches the current allowed limit this method will return an HTTP 403 error.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/tweets/post-and-engage/api-reference/post-statuses-update)
 *
 * @param status The text of the status update. URL encode as necessary. [t.co link wrapping](https://developer.twitter.com/en/docs/basics/tco) will affect character counts.
 * @param inReplyToStatusId The ID of an existing status that the update is in reply to. Note: This parameter will be ignored unless the author of the Tweet this parameter references is mentioned within the status text. Therefore, you must include `@username` , where `username` is the author of the referenced Tweet, within the update.
 * @param autoPopulateReplyMetadata If set to `true` and used with `in_reply_to_status_id`, leading `@mentions` will be looked up from the original Tweet, and added to the new Tweet from there. This wil append `@mentions` into the metadata of an extended Tweet as a reply chain grows, until the limit on `@mentions` is reached. In cases where the original Tweet has been deleted, the reply will fail.
 * @param excludeReplyUserIds When used with `auto_populate_reply_metadata`, a comma-separated list of user ids which will be removed from the server-generated `@mentions` prefix on an extended Tweet. Note that the leading `@mention` cannot be removed as it would break the `in-reply-to-status-id` semantics. Attempting to remove it will be silently ignored.
 * @param attachmentUrl In order for a URL to not be counted in the status body of an extended Tweet, provide a URL as a Tweet attachment. This URL must be a Tweet permalink, or Direct Message deep link. Arbitrary, non-Twitter URLs must remain in the status text. URLs passed to the `attachment_url` parameter not matching either a Tweet permalink or Direct Message deep link will fail at Tweet creation and cause an exception.
 * @param mediaIds A comma-delimited list of `media_ids!`to associate with the Tweet. You may include up to 4 photos or 1 animated GIF or 1 video in a Tweet. See [Uploading Media](https://developer.twitter.com/en/docs/media/upload-media/overview) for further details on uploading media.
 * @param possiblySensitive If you upload Tweet media that might be considered sensitive content such as nudity, or medical procedures, you must set this value to true. See [Media setting and best practices](https://support.twitter.com/articles/20169200) for more context.
 * @param lat The latitude of the location this Tweet refers to. This parameter will be ignored unless it is inside the range -90.0 to +90.0 (North is positive) inclusive. It will also be ignored if there is no corresponding `long` parameter.
 * @param long The longitude of the location this Tweet refers to. The valid ranges for longitude are -180.0 to +180.0 (East is positive) inclusive. This parameter will be ignored if outside that range, if it is not a number, if `geo_enabled` is disabled, or if there no corresponding `lat` parameter.
 * @param placeId A [place](https://developer.twitter.com/en/docs/geo/place-information/overview) in the world.
 * @param displayCoordinates Whether or not to put a pin on the exact coordinates a Tweet has been sent from.
 * @param trimUser When set to either `true` , `t` or `1` , the response will include a user object including only the author's ID. Omit this parameter to receive the complete user object.
 * @param enableDMCommands When set to `true`, enables shortcode commands for sending Direct Messages as part of the status text to send a Direct Message to a user. When set to `false`, disables this behavior and includes any leading characters in the status text that is posted
 * @param failDMCommands When set to `true`, causes any status text that starts with shortcode commands to return an API error. When set to `false`, allows shortcode commands to be sent in the status text and acted on by the API.
 * @param cardUri Associate an ads card with the Tweet using the `card_uri` value from any ads card response.
 */
@OptIn(Twitter4ktInternalAPI::class)
public suspend fun StatusesApi.update(
    status: String,
    inReplyToStatusId: String? = null,
    autoPopulateReplyMetadata: Boolean? = null,
    excludeReplyUserIds: List<String>? = null,
    attachmentUrl: String? = null,
    mediaIds: List<String>? = null,
    possiblySensitive: Boolean? = null,
    lat: Double? = null,
    long: Double? = null,
    placeId: String? = null,
    displayCoordinates: Boolean? = null,
    trimUser: Boolean? = null,
    enableDMCommands: Boolean? = null,
    failDMCommands: Boolean? = null,
    cardUri: String? = null
): ApiResult<Tweet> {
    val url = apiUrl("1.1/statuses/update.json").build()
    val body = Parameters.build {
        appendNotNulls(
            "status" to status,
            "in_reply_to_status_id" to inReplyToStatusId,
            "auto_populate_reply_metadata" to autoPopulateReplyMetadata,
            "exclude_reply_user_ids" to excludeReplyUserIds?.joinToString(","),
            "attachment_url" to attachmentUrl,
            "media_ids" to mediaIds?.joinToString(","),
            "possibly_sensitive" to possiblySensitive,
            "lat" to lat,
            "long" to long,
            "place_id" to placeId,
            "display_coordinates" to displayCoordinates,
            "trim_user" to trimUser,
            "enable_dmcommands" to enableDMCommands,
            "fail_dmcommands" to failDMCommands,
            "card_uri" to cardUri
        )
    }
    return apiClient.postInternal<Tweet>(url, body)
}
