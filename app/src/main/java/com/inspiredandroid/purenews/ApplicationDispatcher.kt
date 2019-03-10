package com.inspiredandroid.purenews

import kotlinx.coroutines.*

internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default

internal actual fun done(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.apply {
        launch(Dispatchers.Main) {
            block()
        }
    }
}

// internal actual val sqlDriver: SqlDriver =  AndroidSqliteDriver(Database.Schema, null, "test.db")
