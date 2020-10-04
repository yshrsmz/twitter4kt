package com.codingfeline.twitter4kt.core

import io.ktor.http.URLBuilder

internal object Urls {
    const val API_ENDPOINT: String = "https://api.twitter.com"
}

public fun apiUrl(path: String, baseUrl: String = Urls.API_ENDPOINT): URLBuilder {
    return URLBuilder(baseUrl).path(path)
}
