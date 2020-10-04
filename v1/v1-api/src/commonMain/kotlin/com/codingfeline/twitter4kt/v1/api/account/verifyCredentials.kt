package com.codingfeline.twitter4kt.v1.api.account

import com.codingfeline.twitter4kt.core.apiUrl
import com.codingfeline.twitter4kt.core.model.ApiResult
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
