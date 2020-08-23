package com.codingfeline.twitter4kt.model.status

import com.codingfeline.twitter4kt.model.Place
import com.codingfeline.twitter4kt.model.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tweet(
    @SerialName("created_at") val createdAt: String,
    @SerialName("id") val id: Long,
    @SerialName("id_str") val idStr: String,
    @SerialName("text") val text: String,
    @SerialName("source") val source: String,
    @SerialName("truncated") val truncated: Boolean,
    @SerialName("in_reply_to_status_id") val inReplyToStatusId: Long?,
    @SerialName("in_reply_to_status_id_str") val inReplyToStatusIdStr: String?,
    @SerialName("in_reply_to_user_id") val inReplyToUserId: Long?,
    @SerialName("in_reply_to_user_id_str") val inReplyToUserIdStr: String?,
    @SerialName("in_reply_to_screen_name") val inReplyToScreenName: String?,
    @SerialName("user") val user: User,
    @SerialName("coordinates") val coordinates: Coordinate?,
    @SerialName("place") val place: Place?,
    @SerialName("quoted_status_id") val quotedStatusId: Long?,
    @SerialName("quoted_status_id_str") val quotedStatusIdStr: String?,
    @SerialName("is_quote_status") val isQuoteStatus: Boolean,
    @SerialName("quoted_status") val quotedStatus: Tweet?,
    @SerialName("retweeted_status") val retweetedStatus: Tweet?,
    @SerialName("quote_count") val quoteCount: Int,
    @SerialName("reply_count") val replyCount: Int,
    @SerialName("retweet_count") val retweetCount: Int,
    @SerialName("favorite_count") val favoriteCount: Int,
    @SerialName("entities") val entities: Entities,
    @SerialName("extended_entities") val extendedEntities: ExtendedEntities?,
    @SerialName("favorited") val favorited: Boolean,
    @SerialName("retweeted") val retweeted: Boolean,
    @SerialName("possibly_sensitive") val possiblySensitive: Boolean,
    @SerialName("filter_level") val filterLevel: String,
    @SerialName("lang") val lang: String?,
    @SerialName("geo") @Deprecated(
        "Use the `coordinates` field instead",
        replaceWith = ReplaceWith("coordinates")
    ) val geo: Coordinate?,
    @SerialName("contributors") val contributors: List<Contributor>?,
) {
    @Serializable
    data class Contributor(
        @SerialName("id") val id: Long,
        @SerialName("id_str") val idStr: String,
        @SerialName("screen_name") val screenName: String
    )

    @Serializable
    data class Coordinate(
        @SerialName("coordinates") val coordinate: List<Float>,
        @SerialName("type") val type: String
    )
}