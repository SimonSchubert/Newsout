package com.inspiredandroid.purenews

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

internal actual var sqlDriver: SqlDriver? = null

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        sqlDriver = AndroidSqliteDriver(SqlDelightDatabase.Schema, applicationContext, "1.db")

        Database.getItemQueries()?.tidy()
    }
}
