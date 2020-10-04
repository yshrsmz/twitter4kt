package com.codingfeline.twitter4kt.v1.api.account

import com.codingfeline.twitter4kt.core.session.ApiClient
import com.codingfeline.twitter4kt.core.session.Endpoint
import com.codingfeline.twitter4kt.core.session.ExtendableApiClient
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI

@OptIn(Twitter4ktInternalAPI::class)
public class AccountApi(
    override val apiClient: ExtendableApiClient
) : Endpoint

@OptIn(Twitter4ktInternalAPI::class)
public val ApiClient.account: AccountApi
    get() = AccountApi(this as ExtendableApiClient)
