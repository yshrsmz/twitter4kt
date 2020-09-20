package com.codingfeline.twitter4kt.v1.model.error

class TwitterApiException(val errors: List<TwitterError>) : Exception() {
}