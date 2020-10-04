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
