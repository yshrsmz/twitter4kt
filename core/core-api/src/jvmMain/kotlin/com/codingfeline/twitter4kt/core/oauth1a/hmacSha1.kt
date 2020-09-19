package com.codingfeline.twitter4kt.core.oauth1a

import io.ktor.http.encodeOAuth
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM_HMAC_SHA1 = "HmacSHA1"

@OptIn(InternalAPI::class)
actual fun hmacSha1(key: String, text: String): String {
    val keySpec = SecretKeySpec(key.toByteArray(), ALGORITHM_HMAC_SHA1)
    return Mac.getInstance(ALGORITHM_HMAC_SHA1).run {
        init(keySpec)
        doFinal(text.toByteArray()).encodeBase64().encodeOAuth()
    }
}