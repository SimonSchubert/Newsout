package com.inspiredandroid.newsout

import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal expect val ApplicationDispatcher: CoroutineDispatcher
expect var sqlDriver: SqlDriver
expect fun setupDatabase()
internal expect fun done(block: suspend CoroutineScope.() -> Unit)
expect var dispatcher: CoroutineDispatcher