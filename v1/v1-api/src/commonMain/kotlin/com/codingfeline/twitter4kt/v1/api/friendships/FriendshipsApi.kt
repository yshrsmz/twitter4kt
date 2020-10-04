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
package com.codingfeline.twitter4kt.v1.api.friendships

import com.codingfeline.twitter4kt.core.session.ApiClient
import com.codingfeline.twitter4kt.core.session.Endpoint
import com.codingfeline.twitter4kt.core.session.ExtendableApiClient
import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI

public class FriendshipsApi @OptIn(Twitter4ktInternalAPI::class) constructor(
    override val apiClient: ExtendableApiClient
) : Endpoint

@OptIn(Twitter4ktInternalAPI::class)
public val ApiClient.friendships: FriendshipsApi
    get() = FriendshipsApi(this as ExtendableApiClient)
