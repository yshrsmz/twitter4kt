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
package com.codingfeline.twitter4kt.v1.model.error

@Suppress("MemberVisibilityCanBePrivate")
public class TwitterApiException(
    public val errors: List<TwitterError>,
) : Exception() {

    /**
     * Check if this exception contains [TwitterError.Code]
     */
    public fun contains(code: TwitterError.Code): Boolean {
        return errors.any { it.rawCode == code.value }
    }

    override fun toString(): String {
        return "TwitterApiException(errors=$errors)"
    }
}
