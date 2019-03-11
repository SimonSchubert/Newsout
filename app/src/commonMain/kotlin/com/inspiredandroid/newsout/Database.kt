package com.inspiredandroid.newsout

import com.inspiredandroid.newsout.SqlDelightDatabase

/* Copyright 2019 Simon Schubert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
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