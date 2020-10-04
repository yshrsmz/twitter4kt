package com.codingfeline.twitter4kt.core.oauth1a

public data class OAuthConfig(
    public val callback: String = "oob",
    public val xAuthAccessType: XAuthAccessType? = null
)

public enum class XAuthAccessType(
    public val value: String
) {
    READ("read"),
    WRITE("write"),
}
