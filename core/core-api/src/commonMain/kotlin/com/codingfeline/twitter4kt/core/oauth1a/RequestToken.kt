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

import com.codingfeline.twitter4kt.core.apiUrl
import com.codingfeline.twitter4kt.core.model.oauth1a.RequestToken
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import com.codingfeline.twitter4kt.core.util.appendNotNulls

@OptIn(Twitter4ktInternalAPI::class)
private fun RequestToken.buildAuthUrlInternal(path: String, forceLogin: Boolean?, screenName: String?): String {
    return apiUrl(path)
        .also { urlBuilder ->
            urlBuilder.parameters.appendNotNulls(
                "oauth_token" to token,
                "force_login" to forceLogin,
                "screen_name" to screenName?.takeIf { it.isNotBlank() }
            )
        }
        .buildString()
}

/**
 * Allows a Consumer application to use an OAuth Request Token to request user authorization.
 * This method fulfills [Section 6.2](http://oauth.net/core/1.0/#auth_step2) of the [OAuth 1.0 authentication flow](http://oauth.net/core/1.0/#anchor9).
 * Desktop applications must use this method (and cannot use [GET oauth/authenticate](https://developer.twitter.com/en/docs/basics/authentication/api-reference/authenticate)).
 *
 * Usage Note: An `oauth_callback` is never sent to this method, provide it to [POST oauth/request_token](https://developer.twitter.com/en/docs/basics/authentication/api-reference/request_token) instead.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/authentication/api-reference/authorize)
 *
 * @param forceLogin Forces the user to enter their credentials to ensure the correct users account is authorized.
 * @param screenName Prefills the username input box of the OAuth login screen with the given value.
 */
public fun RequestToken.authorizationUrl(forceLogin: Boolean? = null, screenName: String? = null): String {
    return buildAuthUrlInternal("oauth/authorize", forceLogin, screenName)
}

/**
 * Allows a Consumer application to use an OAuth request_token to request user authorization.
 *
 * This method is a replacement of Section 6.2 of the OAuth 1.0 authentication flow for applications using the callback authentication flow.
 * The method will use the currently logged in user as the account for access authorization unless the force_login parameter is set to true.
 *
 * This method differs from GET oauth / authorize in that if the user has already granted the application permission, the redirect will occur without the user having to re-approve the application.
 * To realize this behavior, you must enable the Use Sign in with Twitter setting on your application record.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/authentication/api-reference/authenticate)
 *
 * @param forceLogin Forces the user to enter their credentials to ensure the correct users account is authorized.
 * @param screenName Prefills the username input box of the OAuth login screen with the given value.
 */
public fun RequestToken.authenticationUrl(forceLogin: Boolean? = null, screenName: String? = null): String {
    return buildAuthUrlInternal("oauth/authenticate", forceLogin, screenName)
}
