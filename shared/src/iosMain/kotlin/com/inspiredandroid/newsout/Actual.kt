package com.inspiredandroid.newsout

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

internal actual val ApplicationDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(
        private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    dispatch_async(dispatchQueue) {
      block.run()
    }
  }
}

internal actual fun done(block: suspend CoroutineScope.() -> Unit) {
  GlobalScope.apply {
    launch(ApplicationDispatcher) {
      block()
    }
  }
}

actual var sqlDriver: SqlDriver? = null

actual fun setupDatabase() {
  sqlDriver = NativeSqliteDriver(SqlDelightDatabase.Schema, "1.db")
}