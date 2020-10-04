package com.codingfeline.twitter4kt.v1.api.statuses

import com.codingfeline.twitter4kt.core.session.ApiClient
import com.codingfeline.twitter4kt.core.session.Endpoint
import com.codingfeline.twitter4kt.core.session.ExtendableApiClient
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI

@OptIn(Twitter4ktInternalAPI::class)
public class StatusesApi(
    override val apiClient: ExtendableApiClient
) : Endpoint

@OptIn(Twitter4ktInternalAPI::class)
public val ApiClient.statuses: StatusesApi
    get() = StatusesApi(this as ExtendableApiClient)
