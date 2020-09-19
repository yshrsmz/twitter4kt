package com.codingfeline.twitter4kt.core

import io.ktor.http.URLBuilder

object Urls {
    const val API_ENDPOINT = "https://api.twitter.com"
}

fun apiUrl(path: String, baseUrl: String = Urls.API_ENDPOINT): URLBuilder {
    return URLBuilder(baseUrl).path(path)
}