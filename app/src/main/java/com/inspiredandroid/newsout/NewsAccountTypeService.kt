package com.inspiredandroid.newsout

import android.app.Service
import android.content.Intent
import android.os.IBinder

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class NewsAccountTypeService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        val authenticator = NewsAccountAuthenticator(this)
        return authenticator.iBinder
    }
}
