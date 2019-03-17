package com.inspiredandroid.newsout

import com.soywiz.klock.DateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/* Copyright 2019 Simon Schubert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

fun String.isEmailValid(): Boolean = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex().matches(this)