package com.inspiredandroid.newsout

import com.soywiz.klock.DateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

/**
 * Launch in coroutine
 */
fun async(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.apply {
        launch(ApplicationDispatcher) {
            block()
        }
    }
}

/**
 * Get the first image url of of an html image tag
 */
fun String.firstImageUrl(): String {
    val regex = Regex("(?<=<img src=\")[^\"]*")
    val found = regex.find(this)
    return found?.value ?: ""
}

/**
 * Convert boolean to long
 */
fun Boolean.toLong(): Long {
    return if (this) {
        1L
    } else {
        0L
    }
}

/**
 * Convert long to boolean
 */
fun Long.toBoolean(): Boolean {
    return this == 1L
}

/**
 * Is the last fetch > 5 minutes ago
 */
fun User.isCacheOutdated(): Boolean {
    return DateTime.now().unixMillisLong - lastFeedFetch > 1000 * 60 * 5
}

/**
 * Create a snipped of the first 256 plain text characters without html tags and double whitespaces
 */
fun String.snipped(): String {
    var result = Regex("<[^>]*>").replace(this.trim(), "")
    result = Regex("\\\\s+").replace(result, " ")

    return if (result.length <= 256) {
        result
    } else {
        "${result.substring(0, 256).trim()}..."
    }
}

/**
 * Check if email is valid
 */
fun String.isEmailValid(): Boolean = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex().matches(this)