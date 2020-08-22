package com.codingfeline.twitter4kt.core.oauth1a

import com.codingfeline.twitter4kt.core.apiUrl
import com.codingfeline.twitter4kt.model.oauth1a.RequestToken

val RequestToken.authorizationUrl: String
    get() = apiUrl("oauth/authorize")
        .also { it.parameters["oauth_token"] = token }
        .buildString()

val RequestToken.authenticationUrl: String
    get() = apiUrl("oauth/authenticate")
        .also { it.parameters["oauth_token"] = token }
        .buildString()