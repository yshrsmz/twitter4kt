package com.codingfeline.twitter4kt.v1.api

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

expect fun runTest(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend (scope: CoroutineScope) -> Unit
)
