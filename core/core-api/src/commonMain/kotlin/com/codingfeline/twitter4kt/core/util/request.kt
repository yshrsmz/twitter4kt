package com.codingfeline.twitter4kt.core.util

import io.ktor.client.request.forms.FormBuilder
import io.ktor.http.ParametersBuilder

@Twitter4ktInternalAPI
public fun ParametersBuilder.appendNotNulls(vararg values: Pair<String, Any?>) {
    values.filter { it.second != null }
        .map { it.first to it.second!!.toString() }
        .forEach {
            append(it.first, it.second)
        }
}

@Twitter4ktInternalAPI
public fun FormBuilder.appendNotNulls(vararg values: Pair<String, Any?>) {
    values.filter { it.second != null }
        .map { it.first to it.second!!.toString() }
        .forEach {
            append(it.first, it.second)
        }
}
