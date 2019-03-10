package com.inspiredandroid.purenews

object Database {
    const val SORT_UNREADCOUNT = 1L
    const val SORT_TITLE = 3L

    fun getFeedQueries(): FeedQueries? {
        sqlDriver?.let {driver ->
            val database = SqlDelightDatabase(driver)
            return database.feedQueries
        }
        return null
    }

    fun getItemQueries(): ItemQueries? {
        sqlDriver?.let {driver ->
            val database = SqlDelightDatabase(driver)
            return database.itemQueries
        }
        return null
    }

    fun getUserQueries(): UserQueries? {
        sqlDriver?.let {driver ->
            val database = SqlDelightDatabase(driver)
            return database.userQueries
        }
        return null
    }

    fun getFeeds(): MutableList<Feed> {
        val feedQueries = getFeedQueries()
        feedQueries?.let {
            val user = getUser()
            user?.let { u ->
                return when(u.sorting) {
                    SORT_UNREADCOUNT ->
                        if(u.isFolderTop.toBoolean()) {
                            it.selectAllByUnreadCountAndFolder().executeAsList().toMutableList()
                        } else {
                            it.selectAllByUnreadCount().executeAsList().toMutableList()
                        }
                    SORT_TITLE ->
                        if(u.isFolderTop.toBoolean()) {
                            it.selectAllByTitleAndFolder().executeAsList().toMutableList()
                        } else {
                            it.selectAllByTitle().executeAsList().toMutableList()
                        }
                    else -> return arrayListOf()
                }
            }
        }
        return arrayListOf()
    }

    fun getItems(feedId: Long, type: Long): MutableList<Item> {
        val itemQueries = getItemQueries()
        itemQueries?.let {
            return it.selectAllByFeedIdAndType(feedId, type).executeAsList().toMutableList()
        }
        return arrayListOf()
    }

    fun getUser(): User? {
        val userQueries = getUserQueries()
        userQueries?.let {
            return it.selectAll().executeAsOne()
        }
        return null
    }
}