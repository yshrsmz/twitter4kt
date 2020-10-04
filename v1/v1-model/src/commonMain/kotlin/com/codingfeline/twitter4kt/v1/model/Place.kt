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
package com.codingfeline.twitter4kt.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/overview/geo-objects)
 */
@Serializable
public data class Place(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("place_type") val placeType: String,
    @SerialName("name") val name: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("country_code") val countryCode: String,
    @SerialName("country") val country: String,
    @SerialName("bounding_box") val boundingBox: BoundingBox,
    @SerialName("attributes") val attributes: Attribute,
) {
    @Serializable
    public data class Attribute(
        @SerialName("street_address") val streetAddress: String? = null,
        @SerialName("locality") val locality: String? = null,
        @SerialName("region") val region: String? = null,
        @SerialName("iso3") val iso3: String? = null,
        @SerialName("postal_code") val postalCode: String? = null,
        @SerialName("phone") val phone: String? = null,
        @SerialName("twitter") val twitter: String? = null,
        @SerialName("url") val url: String? = null,
        @SerialName("app:id") val appId: String? = null,
        @SerialName("geotagCount") val geotagCount: String? = null,
    )

    @Serializable
    public data class BoundingBox(
        @SerialName("type") val type: String,
        @SerialName("coordinates") val coordinates: List<List<List<Float>>>
    )
}
