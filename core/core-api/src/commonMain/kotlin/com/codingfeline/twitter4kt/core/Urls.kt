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
package com.codingfeline.twitter4kt.core

import io.ktor.http.Parameters
import io.ktor.http.URLBuilder

internal object Urls {
    const val API_ENDPOINT: String = "https://api.twitter.com"
}

public fun apiUrl(
    path: String,
    baseUrl: String = Urls.API_ENDPOINT,
    parameters: Parameters = Parameters.build {},
): URLBuilder {
    val builder = URLBuilder(urlString = baseUrl).path(path)
    builder.parameters.appendAll(parameters)
    builder.parameters.appendMissing("tweet_mode", listOf("extended"))
    return builder
}
