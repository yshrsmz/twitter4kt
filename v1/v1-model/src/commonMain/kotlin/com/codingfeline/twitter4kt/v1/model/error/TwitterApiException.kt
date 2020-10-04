package com.codingfeline.twitter4kt.v1.model.error

public class TwitterApiException(
    public val errors: List<TwitterError>
) : Exception() {
    override fun toString(): String {
        return "TwitterApiException(errors=$errors)"
    }
}
