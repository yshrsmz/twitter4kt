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
package com.codingfeline.twitter4kt.v1.api.account

import com.codingfeline.twitter4kt.core.ApiResult
import com.codingfeline.twitter4kt.core.apiUrl
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import com.codingfeline.twitter4kt.core.util.appendNotNulls
import com.codingfeline.twitter4kt.v1.api.getInternal
import com.codingfeline.twitter4kt.v1.model.account.Account

@OptIn(Twitter4ktInternalAPI::class)
public suspend fun AccountApi.verifyCredentials(
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    includeEmail: Boolean? = null,
): ApiResult<Account> {
    val url = apiUrl("1.1/account/verify_credentials.json")
        .apply {
            parameters.appendNotNulls(
                "include_entities" to includeEntities,
                "skip_status" to skipStatus,
                "include_email" to includeEmail
            )
        }.build()
    val result = apiClient.getInternal<Account>(url)
    println("account/verify_credentials.json: $result")
    return result
}
