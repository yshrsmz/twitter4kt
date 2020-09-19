package com.codingfeline.twitter4kt.core.oauth1a

data class OAuthConfig(
    val callback: String = "oob",
    val xAuthAccessType: XAuthAccessType? = null
)

enum class XAuthAccessType(val value: String) {
    READ("read"),
    WRITE("write"),
}