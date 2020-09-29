package com.codingfeline.twitter4kt.v1.api.friendships

import com.codingfeline.twitter4kt.core.session.ApiClient
import com.codingfeline.twitter4kt.core.session.Endpoint
import com.codingfeline.twitter4kt.core.session.ExtendableApiClient
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI

class FriendshipsApi @OptIn(Twitter4ktInternalAPI::class) constructor(
    override val apiClient: ExtendableApiClient
) : Endpoint

@OptIn(Twitter4ktInternalAPI::class)
val ApiClient.friendships: FriendshipsApi
    get() = FriendshipsApi(this as ExtendableApiClient)