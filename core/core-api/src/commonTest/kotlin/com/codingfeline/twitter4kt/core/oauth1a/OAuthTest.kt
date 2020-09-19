package com.codingfeline.twitter4kt.core.oauth1a

import kotlin.test.Test
import kotlin.test.assertEquals

class OAuthTest {

    @Test
    fun hmacSha1() {
        val result = hmacSha1("A", "B")

        assertEquals(result, "O6I323rTZhS8%2BWqOzLVYS7AmZhQ%3D")
    }
}