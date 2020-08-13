package com.codingfeline.twitter4kt.core

import com.codingfeline.twitter4kt.core.oauth.AuthFlow

class Twitter4kt {

    companion object
}

fun Twitter4kt.launchOAuthFlow(consumerKeys: ConsumerKeys): AuthFlow {
    return AuthFlow(consumerKeys)
}