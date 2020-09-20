package com.codingfeline.twitter4kt.v1.model

sealed class ApiResult<T> {
    data class Success<T>(val value: T) : ApiResult<T>()
    data class Failure<T>(val error: Exception) : ApiResult<T>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    companion object {
        fun <T> success(value: T): Success<T> = Success(value)
        fun <T> failure(error: Exception): Failure<T> = Failure(error)
    }
}