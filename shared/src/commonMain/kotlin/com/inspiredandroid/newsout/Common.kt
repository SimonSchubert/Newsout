package com.inspiredandroid.newsout

import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

internal expect val ApplicationDispatcher: CoroutineDispatcher
expect var sqlDriver: SqlDriver?
internal expect fun done(block: suspend CoroutineScope.() -> Unit)
expect fun setupDatabase()
