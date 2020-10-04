package com.codingfeline.twitter4kt.core.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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

@OptIn(ExperimentalContracts::class)
inline fun <R, T> ApiResult<T>.fold(
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
