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
package com.codingfeline.twitter4kt.core.oauth1a

import io.ktor.http.encodeOAuth
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM_HMAC_SHA1 = "HmacSHA1"

@OptIn(InternalAPI::class)
internal actual fun hmacSha1(key: String, text: String): String {
    val keySpec = SecretKeySpec(key.toByteArray(), ALGORITHM_HMAC_SHA1)
    return Mac.getInstance(ALGORITHM_HMAC_SHA1).run {
        init(keySpec)
        doFinal(text.toByteArray()).encodeBase64().encodeOAuth()
    }
}
