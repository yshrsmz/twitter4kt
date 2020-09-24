package com.codingfeline.twitter4kt.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [Twitter API reference](https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/overview/geo-objects)
 */
@Serializable
data class Place(
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
    data class Attribute(
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
    data class BoundingBox(
        @SerialName("type") val type: String,
        @SerialName("coordinates") val coordinates: List<List<List<Float>>>
    )
}