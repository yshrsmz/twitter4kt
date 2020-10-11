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
    @SerialName("name") val name: String? = null,
    @SerialName("screen_name") val screenName: String? = null,
    @SerialName("location") val location: String? = null,
    // TODO: derived field
    @SerialName("url") val url: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("protected") val protected: Boolean? = null,
    @SerialName("verified") val verified: Boolean? = null,
    @SerialName("followers_count") val followersCount: Int? = null,
    @SerialName("friends_count") val friendsCount: Int? = null,
    @SerialName("listed_count") val listedCount: Int? = null,
    @SerialName("favourites_count") val favouritesCount: Int? = null,
    @SerialName("statuses_count") val statusesCount: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("profile_banner_url") val profileBannerUrl: String? = null,
    @SerialName("profile_image_url_https") val profileImageUrlHttps: String? = null,
    @SerialName("default_profile") val defaultProfile: Boolean? = null,
    @SerialName("default_profile_image") val defaultProfileImage: Boolean? = null,
    @SerialName("withheld_in_countries") val withheldInCountries: List<String>? = null,
    @SerialName("withheld_scope") val withheldScope: String? = null,
    @SerialName("status") val status: Tweet? = null,
)
