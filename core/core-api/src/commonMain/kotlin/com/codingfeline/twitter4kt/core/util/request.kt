package com.codingfeline.twitter4kt.core.util

import io.ktor.http.ParametersBuilder

fun ParametersBuilder.appendNotNulls(vararg values: Pair<String, Any?>) {
    values.filter { it.second != null }
        .map { it.first to it.second!!.toString() }
        .forEach {
            append(it.first, it.second)
        }
}