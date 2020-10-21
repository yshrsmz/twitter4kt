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
package com.codingfeline.twitter4kt.core

import com.codingfeline.twitter4kt.core.util.Twitter4ktInternalAPI
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public sealed class ApiResult<T> {
    public data class Success<T>(val value: T) : ApiResult<T>()
    public data class Failure<T>(val error: Throwable) : ApiResult<T>()

    public fun getOrNull(): T? {
        return when (this) {
            is Success -> this.value
            is Failure -> null
        }
    }

    public fun exceptionOrNull(): Throwable? {
        return when (this) {
            is Failure -> this.error
            is Success -> null
        }
    }

    public companion object {
        public fun <T> success(value: T): Success<T> = Success(value)
        public fun <T> failure(error: Throwable): Failure<T> = Failure(error)
    }
}

@OptIn(ExperimentalContracts::class)
public fun <T> ApiResult<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is ApiResult.Success)
        returns(false) implies (this@isSuccess is ApiResult.Failure)
    }
    return this is ApiResult.Success
}

@OptIn(ExperimentalContracts::class)
public fun <T> ApiResult<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is ApiResult.Failure)
        returns(false) implies (this@isFailure is ApiResult.Success)
    }
    return this is ApiResult.Failure
}

@Suppress("unused", "NOTHING_TO_INLINE")
internal inline fun <T> Result<T>.toApiResult(): ApiResult<T> {
    return when {
        isSuccess -> ApiResult.success(getOrThrow())
        else -> ApiResult.failure(requireNotNull(exceptionOrNull()))
    }
}

@Suppress("unused")
public fun <T> ApiResult<T>.getOrThrow(): T {
    return when (this) {
        is ApiResult.Failure -> throw error
        is ApiResult.Success -> value
    }
}

@Suppress("unused")
@OptIn(ExperimentalContracts::class)
public inline fun <R, T : R> ApiResult<T>.getOrElse(onFailure: (exception: Throwable) -> R): R {
    contract {
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is ApiResult.Failure -> onFailure(error)
        is ApiResult.Success -> value
    }
}

@Suppress("unused")
public fun <R, T : R> ApiResult<T>.getOrDefault(defaultValue: R): R {
    return when (this) {
        is ApiResult.Failure -> defaultValue
        is ApiResult.Success -> value
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

@Twitter4ktInternalAPI
public inline fun <T, R> ApiResult<T>.runCatching(block: ApiResult<T>.() -> R): ApiResult<R> {
    return try {
        ApiResult.success(block())
    } catch (e: Throwable) {
        ApiResult.failure(e)
    }
}

@Suppress("unused")
@OptIn(ExperimentalContracts::class)
public inline fun <R, T> ApiResult<T>.map(transform: (value: T) -> R): ApiResult<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is ApiResult.Success -> ApiResult.success(transform(value))
        is ApiResult.Failure -> ApiResult.failure(error)
    }
}

@Suppress("unused")
@OptIn(Twitter4ktInternalAPI::class)
public inline fun <R, T> ApiResult<T>.mapCatching(transform: (value: T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> runCatching { transform(value) }
        is ApiResult.Failure -> ApiResult.failure(error)
    }
}

@Suppress("unused")
@OptIn(ExperimentalContracts::class)
public inline fun <R, T : R> ApiResult<T>.recover(transform: (value: Throwable) -> R): ApiResult<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is ApiResult.Success -> ApiResult.success(value)
        is ApiResult.Failure -> ApiResult.success(transform(error))
    }
}

@Suppress("unused")
@OptIn(Twitter4ktInternalAPI::class)
public inline fun <R, T : R> ApiResult<T>.recoverCatching(transform: (value: Throwable) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> ApiResult.success(value)
        is ApiResult.Failure -> runCatching { transform(error) }
    }
}

@Suppress("unused")
@OptIn(ExperimentalContracts::class)
public inline fun <T> ApiResult<T>.onFailure(action: (exception: Throwable) -> Unit): ApiResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    exceptionOrNull()?.let(action)
    return this
}

@Suppress("unused")
@OptIn(ExperimentalContracts::class)
public inline fun <T> ApiResult<T>.onSuccess(action: (value: T) -> Unit): ApiResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    getOrNull()?.let(action)
    return this
}
