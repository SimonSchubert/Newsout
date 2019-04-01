package com.inspiredandroid.newsout

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
// internal actual var sqlDriver: SqlDriver? = null

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        sqlDriver = AndroidSqliteDriver(SqlDelightDatabase.Schema, applicationContext, "1.db")

        Database.getItemQueries()?.clearOld()
    }
}
