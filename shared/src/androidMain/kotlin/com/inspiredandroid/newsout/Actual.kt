package com.inspiredandroid.newsout

import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.*

internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default

internal actual fun done(block: suspend CoroutineScope.() -> Unit) {
  GlobalScope.apply {
    launch(Dispatchers.Main) {
      block()
    }
  }
}

actual var sqlDriver: SqlDriver? = null

actual fun setupDatabase() {
}

actual var dispatcher: CoroutineDispatcher = Dispatchers.IO