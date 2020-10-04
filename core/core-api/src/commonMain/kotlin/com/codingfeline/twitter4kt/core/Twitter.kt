package com.codingfeline.twitter4kt.core

import com.codingfeline.twitter4kt.core.model.oauth1a.AccessToken
import com.codingfeline.twitter4kt.core.oauth1a.OAuth1aFlow
import com.codingfeline.twitter4kt.core.oauth1a.OAuthConfig
import com.codingfeline.twitter4kt.core.session.ApiClient

@Suppress("FunctionName")
public fun Twitter(block: TwitterConfig.Builder.() -> Unit): Twitter {
    return Twitter(config = TwitterConfig.Builder().apply(block).build())
}

public class Twitter(
    internal val config: TwitterConfig
) {

    public companion object
}


public fun Twitter.launchOAuth1aFlow(oAuthConfig: OAuthConfig = OAuthConfig()): OAuth1aFlow {
    return OAuth1aFlow(
        consumerKeys = config.consumerKeys,
        oAuthConfig = oAuthConfig,
        httpClientConfig = config.httpClientConfig,
    )
}

public fun Twitter.startSession(accessToken: AccessToken): ApiClient {
    return ApiClient(
        consumerKeys = config.consumerKeys,
        accessToken = accessToken,
        clock = config.clock,
        httpClientConfig = config.httpClientConfig,
    )
}
