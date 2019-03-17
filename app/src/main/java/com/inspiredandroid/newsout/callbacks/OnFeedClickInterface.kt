package com.inspiredandroid.newsout.callbacks

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
interface OnFeedClickInterface {
    fun onClickFeed(id: Long, title: String, type: Long)
    fun onLongClickFeed(id: Long, title: String, isFolder: Boolean)
}