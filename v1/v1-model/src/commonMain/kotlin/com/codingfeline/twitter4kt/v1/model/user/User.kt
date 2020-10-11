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
package com.codingfeline.twitter4kt.v1.model.user

import com.codingfeline.twitter4kt.v1.model.status.Tweet
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/overview/user-object)
 */
@Serializable
public data class User(
    @SerialName("id") val id: Long,
    @SerialName("id_str") val idStr: String,
    @SerialName("name") val name: String,
    @SerialName("screen_name") val screenName: String,
    @SerialName("location") val location: String?,
    // TODO: derived field
    @SerialName("url") val url: String?,
    @SerialName("description") val description: String? = null,
    @SerialName("protected") val protected: Boolean,
    @SerialName("verified") val verified: Boolean,
    @SerialName("followers_count") val followersCount: Int,
    @SerialName("friends_count") val friendsCount: Int,
    @SerialName("listed_count") val listedCount: Int,
    @SerialName("favourites_count") val favouritesCount: Int,
    @SerialName("statuses_count") val statusesCount: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("profile_banner_url") val profileBannerUrl: String,
    @SerialName("profile_image_url_https") val profileImageUrlHttps: String,
    @SerialName("default_profile") val defaultProfile: Boolean,
    @SerialName("default_profile_image") val defaultProfileImage: Boolean,
    @SerialName("withheld_in_countries") val withheldInCountries: List<String>? = null,
    @SerialName("withheld_scope") val withheldScope: String? = null,
    @SerialName("status") val status: Tweet? = null,
)
