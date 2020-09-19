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
    @SerialName("place_type") val placeType: PlaceType,
    @SerialName("name") val name: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("country_code") val countryCode: String,
    @SerialName("country") val country: String,
    @SerialName("boundingBox") val boundingBox: BoundingBox,
    @SerialName("attributes") val attributes: Attribute,
) {
    @Serializable
    data class Attribute(
        @SerialName("street_address") val streetAddress: String?,
        @SerialName("locality") val locality: String?,
        @SerialName("region") val region: String?,
        @SerialName("iso3") val iso3: String?,
        @SerialName("postal_code") val postalCode: String?,
        @SerialName("phone") val phone: String?,
        @SerialName("twitter") val twitter: String?,
        @SerialName("url") val url: String?,
        @SerialName("app:id") val appId: String?,
        @SerialName("geotagCount") val geotagCount: String?,
    )

    @Serializable
    data class BoundingBox(
        @SerialName("type") val type: String,
        @SerialName("coordinates") val coordinates: List<List<List<Float>>>
    )

    enum class PlaceType(val value: String) {
        Point("Point"),
        Polygon("Polygon")
    }
}