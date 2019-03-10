package com.inspiredandroid.purenews

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NewsAccountTypeService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        val authenticator = NewsAccountAuthenticator(this)
        return authenticator.iBinder
    }
}
