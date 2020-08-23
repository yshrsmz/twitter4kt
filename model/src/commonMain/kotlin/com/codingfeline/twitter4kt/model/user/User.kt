package com.codingfeline.twitter4kt.model.user

import com.codingfeline.twitter4kt.model.status.Tweet
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/overview/user-object)
 */
@Serializable
data class User(
    @SerialName("id") val id: Long,
    @SerialName("id_str") val idStr: String,
    @SerialName("name") val name: String,
    @SerialName("screen_name") val screenName: String,
    @SerialName("location") val location: String?,
    // TODO: derived field
    @SerialName("url") val url: String?,
    @SerialName("description") val description: String?,
    @SerialName("protected") val protected: Boolean,
    @SerialName("verified") val verified: Boolean,
    @SerialName("followers_count") val followersCount: Boolean,
    @SerialName("friend_count") val friendsCount: Boolean,
    @SerialName("listed_count") val listedCount: Int,
    @SerialName("favourites_count") val favouritesCount: Int,
    @SerialName("statuses_count") val statusesCount: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("profile_banner_url") val profileBannerUrl: String,
    @SerialName("profile_image_url_https") val profileImageUrlHttps: String,
    @SerialName("default_profile") val defaultProfile: Boolean,
    @SerialName("default_profile_image") val defaultProfileImage: Boolean,
    @SerialName("withheld_in_countries") val withheldInCountries: List<String>?,
    @SerialName("withheld_scope") val withheldScope: String?,
    @SerialName("status") val status: Tweet?,
)