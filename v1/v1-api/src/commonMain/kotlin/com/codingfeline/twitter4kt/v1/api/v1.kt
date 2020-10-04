package com.codingfeline.twitter4kt.v1.api

import com.codingfeline.twitter4kt.core.model.ApiResult
import com.codingfeline.twitter4kt.core.session.ExtendableApiClient
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import com.codingfeline.twitter4kt.v1.model.error.TwitterApiException
import com.codingfeline.twitter4kt.v1.model.error.TwitterError
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.Parameters
import io.ktor.http.Url
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
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
    } catch (e: ClientRequestException) {
        ApiResult.failure(e)
    } catch (e: Exception) {
        ApiResult.failure(e)
    }
}

@OptIn(Twitter4ktInternalAPI::class)
internal suspend inline fun <reified T> ExtendableApiClient.postInternal(url: Url, body: Parameters): ApiResult<T> {
    return try {
        val result = httpClient.post<JsonObject>(url) {
            this.body = FormDataContent(body)
        }
        if (result.containsKey("errors")) {
            ApiResult.failure(TwitterError.fromJsonObject(json, result).asException())
        } else {
            ApiResult.success(json.decodeFromJsonElement(result))
        }
    } catch (e: ClientRequestException) {
        val resultException = e.asTwitterApiException(json) ?: e
        ApiResult.failure(resultException)
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

suspend fun ClientRequestException.asTwitterApiException(json: Json): TwitterApiException? {
    val resText = response.content.readUTF8Line() ?: return null
    val jsonRes = json.decodeFromString<JsonObject>(resText)
    if (jsonRes.containsKey("errors")) {
        val errors = TwitterError.fromJsonObject(json, jsonRes)
        return TwitterApiException(errors)
    } else {
        return null
    }
}

fun List<TwitterError>.asException(): TwitterApiException = TwitterApiException(this)
