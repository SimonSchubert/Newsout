package com.inspiredandroid.newsout

import com.squareup.sqldelight.Query

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
object Database {
    const val SORT_UNREADCOUNT = 1L
    const val SORT_TITLE = 3L
    const val TYPE_FEED = 0L
    const val TYPE_FOLDER = 1L
    const val TYPE_STARRED = -1L
    const val TYPE_UNREAD = -2L

    fun setup() {
        setupDatabase()
    }

    /**
     * Gets the feed queries
     */
    fun getFeedQueries(): FeedQueries {
        val database = SqlDelightDatabase(sqlDriver)
        return database.feedQueries
    }

    /**
     * Gets the item queries
     */
    fun getItemQueries(): ItemQueries {
        val database = SqlDelightDatabase(sqlDriver)
        return database.itemQueries
    }

    /**
     * Get the user queries
     */
    fun getUserQueries(): UserQueries {
        val database = SqlDelightDatabase(sqlDriver)
        return database.userQueries
    }

    /**
     * Get all feeds sorted by unread count or title based on the the user settings
     */
    fun getFeeds(): MutableList<Feed> {
        val user = getUser()
        return when (user.sorting) {
            SORT_UNREADCOUNT ->
                if (user.isFolderTop.toBoolean()) {
                    getFeedQueries().selectAllByUnreadCountAndFolder().executeAsList().toMutableList()
                } else {
                    getFeedQueries().selectAllByUnreadCount().executeAsList().toMutableList()
                }
            SORT_TITLE ->
                if (user.isFolderTop.toBoolean()) {
                    getFeedQueries().selectAllByTitleAndFolder().executeAsList().toMutableList()
                } else {
                    getFeedQueries().selectAllByTitle().executeAsList().toMutableList()
                }
            else -> arrayListOf()
        }
    }

    /**
     * Get items by feed or folder
     */
    fun getItems(feedId: Long, type: Long): MutableList<Item> {
        return when (type) {
            TYPE_UNREAD -> getItemQueries().selectUnread().executeAsList().toMutableList()
            TYPE_STARRED -> getItemQueries().selectStarred().executeAsList().toMutableList()
            else -> getItemQueries().selectAllByFeedIdAndType(feedId, type).executeAsList().toMutableList()
        }
    }

    /**
     * Get items by feed or folder and search by query
     */
    fun getItemsByQuery(feedId: Long, type: Long, query: String): MutableList<Item> {
        return getItemQueries().selectAllByFeedIdAndTypeAndQuery(feedId, type, query).executeAsList().toMutableList() ?: arrayListOf()
    }

    /**
     * Get the only and one user from the database
     */
    fun getUser(): User {
        return getUserQueries().selectAll().executeAsOne()
    }

    /**
     * Get number of unread articles
     */
    fun getTotalUnreadCount(): Long {
        return try {
            getFeedQueries().countUnread().executeAsOne()
        } catch (ignore: Throwable) {
            0L
        }
    }

    /**
     * Get number of starred articles
     */
    fun getTotalStarredCount(): Long {
        return getItemQueries().countStarred().executeAsOne()
    }

    /**
     * Clear feeds and items
     */
    fun clear() {
        getFeedQueries().clear()
        getItemQueries().clear()
    }
}