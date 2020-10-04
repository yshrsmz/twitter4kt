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
package com.codingfeline.twitter4kt.core.util

import io.ktor.client.request.forms.FormBuilder
import io.ktor.http.ParametersBuilder

@Twitter4ktInternalAPI
public fun ParametersBuilder.appendNotNulls(vararg values: Pair<String, Any?>) {
    values.filter { it.second != null }
        .map { it.first to it.second!!.toString() }
        .forEach {
            append(it.first, it.second)
        }
}

@Twitter4ktInternalAPI
public fun FormBuilder.appendNotNulls(vararg values: Pair<String, Any?>) {
    values.filter { it.second != null }
        .map { it.first to it.second!!.toString() }
        .forEach {
            append(it.first, it.second)
        }
}
