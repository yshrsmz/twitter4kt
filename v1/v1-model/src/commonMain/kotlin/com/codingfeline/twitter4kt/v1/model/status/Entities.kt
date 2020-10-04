package com.codingfeline.twitter4kt.v1.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Entities(
    @SerialName("hashtags") val hashtags: List<HashtagEntity>,
    @SerialName("media") val media: List<MediaEntity> = emptyList(),
    @SerialName("urls") val urls: List<URLEntity>,
    @SerialName("user_mentions") val userMentions: List<UserMentionEntity>,
    @SerialName("symbols") val symbols: List<SymbolEntity>,
    // TODO: polls field
    // https://github.com/yshrsmz/twitter4kt/issues/10
    // https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/overview/entities-object#polls
) {
    @Serializable
    public data class HashtagEntity(
        @SerialName("indices") val indices: List<Int>,
        @SerialName("text") val text: String
    )


    @Serializable
    public data class MediaEntity(
        @SerialName("display_url") val displayUrl: String,
        @SerialName("expanded_url") val expandedUrl: String,
        @SerialName("id") val id: Long,
        @SerialName("id_str") val idStr: String,
        @SerialName("indices") val indices: List<Int>,
        @SerialName("media_url") val mediaUrl: String,
        @SerialName("media_url_https") val mediaUrlHttps: String,
        @SerialName("sizes") val sizes: SizeList,
        @SerialName("source_status_id") val sourceStatusId: Long,
        @SerialName("source_status_id_str") val sourceStatusIdStr: String,
        @SerialName("type") val type: String,
        @SerialName("url") val url: String,
        @SerialName("video_info") val videoInfo: VideoInfo?,
        @SerialName("additional_media_info") val additionalMediaInfo: AdditionalMediaInfo?,
    ) {
        @Serializable
        public data class SizeList(
            @SerialName("thumb") val thumb: Size,
            @SerialName("large") val large: Size,
            @SerialName("medium") val medium: Size,
            @SerialName("small") val small: Size
        ) {
            @Serializable
            public data class Size(
                @SerialName("w") val w: Int,
                @SerialName("h") val h: Int,
                @SerialName("resize") val resize: String
            )
        }

        @Serializable
        public data class VideoInfo(
            @SerialName("aspect_ratio") val aspectRatio: List<Int>,
            @SerialName("duration_millis") val durationMillis: Int,
            @SerialName("variants") val variants: List<Variant>
        ) {
            @Serializable
            public data class Variant(
                @SerialName("bitrate") val bitrate: Int?,
                @SerialName("content_type") val contentType: String,
                @SerialName("url") val url: String
            )
        }

        @Serializable
        public data class AdditionalMediaInfo(
            @SerialName("title") val title: String,
            @SerialName("description") val description: String,
            @SerialName("embeddable") val embeddable: Boolean,
            @SerialName("monetizable") val monetizable: Boolean
        )
    }


    @Serializable
    public data class URLEntity(
        @SerialName("display_url") val displayUrl: String,
        @SerialName("expanded_url") val expandedUrl: String,
        @SerialName("indices") val indices: List<Int>,
        @SerialName("url") val url: String,
        // TODO: enrichment
        // https://github.com/yshrsmz/twitter4kt/issues/9
        // https://developer.twitter.com/en/docs/twitter-api/v1/enrichments/overview/expanded-and-enhanced-urls
    )

    @Serializable
    public data class UserMentionEntity(
        @SerialName("id") val id: Long,
        @SerialName("id_str") val idStr: String,
        @SerialName("indices") val indices: List<Int>,
        @SerialName("name") val name: String,
        @SerialName("screen_name") val screenName: String,
    )

    @Serializable
    public data class SymbolEntity(
        @SerialName("indices") val indices: List<Int>,
        @SerialName("text") val text: String
    )
}
