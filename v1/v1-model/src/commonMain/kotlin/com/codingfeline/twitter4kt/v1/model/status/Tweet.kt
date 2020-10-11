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
package com.codingfeline.twitter4kt.v1.model.status

import com.codingfeline.twitter4kt.v1.model.Place
import com.codingfeline.twitter4kt.v1.model.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Tweet(
    @SerialName("created_at") val createdAt: String,
    @SerialName("id") val id: Long,
    @SerialName("id_str") val idStr: String,
    @SerialName("text") val text: String? = null,
    @SerialName("full_text") val fullText: String? = null,
    @SerialName("source") val source: String,
    @SerialName("truncated") val truncated: Boolean,
    @SerialName("in_reply_to_status_id") val inReplyToStatusId: Long? = null,
    @SerialName("in_reply_to_status_id_str") val inReplyToStatusIdStr: String? = null,
    @SerialName("in_reply_to_user_id") val inReplyToUserId: Long? = null,
    @SerialName("in_reply_to_user_id_str") val inReplyToUserIdStr: String? = null,
    @SerialName("in_reply_to_screen_name") val inReplyToScreenName: String? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("coordinates") val coordinates: Coordinate? = null,
    @SerialName("place") val place: Place? = null,
    @SerialName("quoted_status_id") val quotedStatusId: Long? = null,
    @SerialName("quoted_status_id_str") val quotedStatusIdStr: String? = null,
    @SerialName("is_quote_status") val isQuoteStatus: Boolean,
    @SerialName("quoted_status") val quotedStatus: Tweet? = null,
    @SerialName("retweeted_status") val retweetedStatus: Tweet? = null,
    @SerialName("quote_count") val quoteCount: Int? = null,
    @SerialName("reply_count") val replyCount: Int? = null,
    @SerialName("retweet_count") val retweetCount: Int,
    @SerialName("favorite_count") val favoriteCount: Int,
    @SerialName("entities") val entities: Entities,
    @SerialName("extended_entities") val extendedEntities: ExtendedEntities? = null,
    @SerialName("favorited") val favorited: Boolean,
    @SerialName("retweeted") val retweeted: Boolean,
    @SerialName("possibly_sensitive") val possiblySensitive: Boolean? = null,
    @SerialName("filter_level") val filterLevel: String? = null,
    @SerialName("lang") val lang: String? = null,
    @SerialName("geo") @Deprecated(
        "Use the `coordinates` field instead",
        replaceWith = ReplaceWith("coordinates")
    ) val geo: Coordinate?,
    @SerialName("contributors") val contributors: List<Contributor>? = emptyList(),
) {
    @Serializable
    public data class Contributor(
        @SerialName("id") val id: Long,
        @SerialName("id_str") val idStr: String,
        @SerialName("screen_name") val screenName: String
    )

    @Serializable
    public data class Coordinate(
        @SerialName("coordinates") val coordinate: List<Float>,
        @SerialName("type") val type: String
    )
}
