package com.inspiredandroid.newsout.callbacks

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
interface OnEditFeedInterface {
    /**
     * Edit feed
     */
    fun onEditFeed(id: Long, title: String, isFolder: Boolean)

    /**
     * Delete feed
     */
    fun onDeleteFeed(id: Long, title: String, isFolder: Boolean)
}