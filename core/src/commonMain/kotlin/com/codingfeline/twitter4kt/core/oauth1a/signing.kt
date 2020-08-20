package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.ConsumerKeys

fun initialBaseNonSignedOAuthHeaders(
    consumerKeys: ConsumerKeys,
    accessToken: AccessToken?,
    nonce: String,
    timestamp: String,
): MutableMap<String, String> {
    return mutableMapOf(
        "oauth_consumer_key" to consumerKeys.key,
        "oauth_nonce" to nonce,
        "oauth_signature_method" to "HMAC-SHA1",
        "oauth_timestamp" to timestamp,
        "oauth_version" to "1.0",
    ).apply {
        if (accessToken != null) {
            this["oauth_token"] = accessToken.token
        }
    }
}

fun initialNonSignedOAuthFlowHeaders(
    consumerKeys: ConsumerKeys,
    oAuthConfig: OAuthConfig,
    nonce: String,
    timestamp: String,
): MutableMap<String, String> {
    return initialBaseNonSignedOAuthHeaders(consumerKeys, null, nonce, timestamp)
        .apply {
            this["oauth_callback"] = oAuthConfig.callback
            if (oAuthConfig.xAuthAccessType != null) {
                this["x_auth_access_type"] = oAuthConfig.xAuthAccessType.value
            }
        }
}

expect fun hmacSha1(key: String, text: String): String