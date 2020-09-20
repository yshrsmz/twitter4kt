package com.codingfeline.twitter4kt.v1.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

actual fun runTest(
    context: CoroutineContext,
    block: suspend (scope: CoroutineScope) -> Unit
) = runBlocking(context) { block(this) }