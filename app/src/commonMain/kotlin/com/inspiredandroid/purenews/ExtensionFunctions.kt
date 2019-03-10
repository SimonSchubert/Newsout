package com.inspiredandroid.purenews

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

fun Boolean.toLong(): Long {
    return if(this) {
        1L
    } else {
        0L
    }
}

fun Long.toBoolean(): Boolean {
    return this == 1L
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