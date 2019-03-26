package com.inspiredandroid.newsout

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UnitTest {

    @Test
    fun testDatabaseClear() {
        val feedId = 1L
        val isFolder = 0L

        Database.getFeedQueries()?.insert(feedId, "", "", 0, 0, isFolder)
        Database.getItemQueries()?.insert(0, feedId, "Test", "", "", 0, isFolder)

        assertEquals(Database.getFeeds().size, 1)
        assertEquals(Database.getItems(feedId, isFolder).size, 1)

        Database.clear()

        assertTrue(Database.getFeeds().isEmpty())
        assertTrue(Database.getItems(0, 0).isEmpty())
    }

    @Test
    fun testDatabaseMarkAsRead() {
        val firstFeedId = 1L
        val isFolder = 0L

        // Increase feed counter
        val feedQueries = Database.getFeedQueries()
        feedQueries?.insert(firstFeedId, "Kotlin", "", 4, 0, isFolder)
        feedQueries?.decreaseUnreadCount(firstFeedId, isFolder)

        assertTrue {
            feedQueries?.selectAllByTitle()?.executeAsOne()?.unreadCount == 3L
        }


        // Mark complete feed as read
        val itemQueries = Database.getItemQueries()
        itemQueries?.insert(10, firstFeedId, "News #1", "", "", 1, isFolder)

        assertTrue { itemQueries?.selectAllByFeedIdAndType(firstFeedId, isFolder)?.executeAsOne()?.isUnread == 1L }
        itemQueries?.markItemAsRead(10)

        assertTrue { itemQueries?.selectAllByFeedIdAndType(firstFeedId, isFolder)?.executeAsOne()?.isUnread == 0L }


        // Mark complete feed as read
        val secondFeedId = 2L

        itemQueries?.insert(11, secondFeedId, "News #2", "", "", 1, isFolder)
        itemQueries?.markAsRead(secondFeedId, isFolder)

        assertTrue { itemQueries?.selectAllByFeedIdAndType(secondFeedId, isFolder)?.executeAsOne()?.isUnread == 0L }


        // mark every item as read
        val thirdFeedId = 3L

        itemQueries?.insert(12, thirdFeedId, "News #3", "", "", 1, isFolder)
        itemQueries?.markAllAsRead()

        assertTrue { itemQueries?.selectAllByFeedIdAndType(thirdFeedId, isFolder)?.executeAsOne()?.isUnread == 0L }
    }
}