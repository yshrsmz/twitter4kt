package com.codingfeline.twitter4kt.core

import com.codingfeline.twitter4kt.core.oauth1a.OAuth1aFlow
import com.codingfeline.twitter4kt.core.session.ApiClient

fun Twitter(block: TwitterConfig.Builder.() -> Unit): Twitter {
    return Twitter(config = TwitterConfig.Builder().apply(block).build())
}

class Twitter(
    internal val config: TwitterConfig
) {

    companion object
}


fun Twitter.launchOAuth1aFlow(): OAuth1aFlow {
    return OAuth1aFlow(config.consumerKeys)
}

fun Twitter.startSession(): ApiClient {
    return TODO()
}