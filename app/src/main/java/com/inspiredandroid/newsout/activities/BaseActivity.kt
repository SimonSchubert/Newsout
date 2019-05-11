package com.inspiredandroid.newsout.activities;

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity() {

    internal var jobs = arrayListOf<CoroutineScope>()

    override fun onPause() {
        super.onPause()

        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}
