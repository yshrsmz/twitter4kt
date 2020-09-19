package com.codingfeline.twitter4kt.core

import kotlin.random.Random

private val NONCE_CHARS = arrayOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
    'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
    '4', '5', '6', '7', '8', '9'
)

fun getNonce(seed: Long, size: Int = 32): String {
    val random = Random(seed)
    val charLength = NONCE_CHARS.size

    return (0 until size).map { NONCE_CHARS[random.nextInt(charLength)] }
        .joinToString("")
}