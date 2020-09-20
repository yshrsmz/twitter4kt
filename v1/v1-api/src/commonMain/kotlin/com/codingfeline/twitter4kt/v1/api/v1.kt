package com.codingfeline.twitter4kt.v1.api

import com.codingfeline.twitter4kt.core.session.ExtendableApiClient
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import com.codingfeline.twitter4kt.v1.model.ApiResult
import com.codingfeline.twitter4kt.v1.model.error.TwitterApiException
import com.codingfeline.twitter4kt.v1.model.error.TwitterError
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray

@OptIn(Twitter4ktInternalAPI::class)
internal suspend inline fun <reified T> ExtendableApiClient.getInternal(url: Url): ApiResult<T> {
    return try {
        val result = httpClient.get<JsonObject>(url)
        if (result.containsKey("errors")) {
            ApiResult.failure(TwitterError.fromJsonObject(json, result).asException())
        } else {
            ApiResult.success(json.decodeFromJsonElement(result))
        }
    } catch (e: Exception) {
        ApiResult.failure(e)
    }
}

fun TwitterError.Companion.fromJsonObject(json: Json, jsonObject: JsonObject): List<TwitterError> {
    val errors = jsonObject.getValue("errors").jsonArray
    return if (errors.isEmpty()) {
        emptyList()
    } else {
        json.decodeFromJsonElement(ListSerializer(TwitterError.serializer()), errors)
    }
}

fun List<TwitterError>.asException(): TwitterApiException = TwitterApiException(this)