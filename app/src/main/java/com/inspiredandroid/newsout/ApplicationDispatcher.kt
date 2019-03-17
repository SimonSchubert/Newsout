package com.inspiredandroid.newsout

import kotlinx.coroutines.*

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default

internal actual fun done(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.apply {
        launch(Dispatchers.Main) {
            block()
        }
    }
}