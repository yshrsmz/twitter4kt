package com.codingfeline.twitter4kt.core.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public sealed class ApiResult<T> {
    public data class Success<T>(val value: T) : ApiResult<T>()
    public data class Failure<T>(val error: Exception) : ApiResult<T>()

    public val isSuccess: Boolean get() = this is Success
    public val isFailure: Boolean get() = this is Failure

    public companion object {
        public fun <T> success(value: T): Success<T> = Success(value)
        public fun <T> failure(error: Exception): Failure<T> = Failure(error)
    }
}

@OptIn(ExperimentalContracts::class)
public inline fun <R, T> ApiResult<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    @Suppress("UNCHECKED_CAST")
    return when (this) {
        is ApiResult.Success -> onSuccess(value)
        is ApiResult.Failure -> onFailure(error)
    }
}
