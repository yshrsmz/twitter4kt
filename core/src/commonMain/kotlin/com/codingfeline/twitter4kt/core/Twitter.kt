package com.codingfeline.twitter4kt.core

import com.codingfeline.twitter4kt.core.oauth1a.OAuth1aFlow
import com.codingfeline.twitter4kt.core.oauth1a.OAuthConfig
import com.codingfeline.twitter4kt.core.session.ApiClient
import com.codingfeline.twitter4kt.model.oauth1a.AccessToken

fun Twitter(block: TwitterConfig.Builder.() -> Unit): Twitter {
    return Twitter(config = TwitterConfig.Builder().apply(block).build())
}

class Twitter(
    internal val config: TwitterConfig
) {

    companion object
}


fun Twitter.launchOAuth1aFlow(oAuthConfig: OAuthConfig = OAuthConfig()): OAuth1aFlow {
    return OAuth1aFlow(
        consumerKeys = config.consumerKeys,
        oAuthConfig = oAuthConfig,
        httpClientConfig = config.httpClientConfig,
    )
}

fun Twitter.startSession(accessToken: AccessToken): ApiClient {
    return ApiClient(
        consumerKeys = config.consumerKeys,
        accessToken = accessToken,
        clock = config.clock,
        httpClientConfig = config.httpClientConfig,
    )
}