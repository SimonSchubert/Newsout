package com.inspiredandroid.newsout

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

/**
 * Convert to dp
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Convert to pixel
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Hide the keyboard
 */
fun Activity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Check if activity is still there
 */
fun Activity.isThere(): Boolean {
    return !isDestroyed && !isFinishing
}

/**
 * Get number of columns based on 300 dp
 */
fun RecyclerView.calculateNumberOfColumns(): Int {
    val displayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    var columns = (dpWidth / 300).toInt()
    if (columns < 1) {
        columns = 1
    }
    return columns
}