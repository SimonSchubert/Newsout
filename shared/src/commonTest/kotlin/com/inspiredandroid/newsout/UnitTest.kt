package com.inspiredandroid.newsout

//import com.soywiz.klock.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

open class UnitTest {

    @Test
    fun testClearDatabase() {
        Database.setup()

        Database.clear()
        Database.getUserQueries()?.insert()

        val feedId = 1L
        val isFolder = false.toLong()

        Database.getFeedQueries()?.insert(feedId, "", "", 0, 0, isFolder)
        Database.getItemQueries()?.insert(0, "", feedId, "Test", "", "", 0, isFolder, 0)

        assertEquals(1, Database.getFeeds().size)
        assertEquals(1, Database.getItems(feedId, isFolder).size)

        Database.clear()
        Database.getUserQueries()?.insert()

        assertTrue(Database.getFeeds().isEmpty())
        assertTrue(Database.getItems(feedId, isFolder).isEmpty())
    }

    @Test
    fun testMarkItemAsRead() {
        Database.clear()

        val feedId = 1L
        val itemId = 10L
        val isFolder = false.toLong()

        val itemQueries = Database.getItemQueries()
        itemQueries?.insert(itemId, "", feedId, "News #1", "", "", 1, isFolder, 0)

        assertTrue { itemQueries?.selectAllByFeedIdAndType(feedId, isFolder)?.executeAsOne()?.isUnread == 1L }

        itemQueries?.markItemAsRead(itemId)

        assertTrue { itemQueries?.selectAllByFeedIdAndType(feedId, isFolder)?.executeAsOne()?.isUnread == 0L }
    }

    @Test
    fun testMarkFeedAsRead() {
        Database.clear()

        val feedId = 1L
        val isFolder = false.toLong()

        val itemQueries = Database.getItemQueries()
        itemQueries?.insert(11, "", feedId, "News #2", "", "", 1, isFolder, 0)
        itemQueries?.markAsRead(feedId, isFolder)

        assertEquals(itemQueries?.selectAllByFeedIdAndType(feedId, isFolder)?.executeAsOne()?.isUnread, 0L)
    }

    @Test
    fun testMarkAllItemsAsRead() {
        Database.clear()

        val feedId = 1L
        val isFolder = false.toLong()

        val itemQueries = Database.getItemQueries()
        itemQueries?.insert(12, "", feedId, "News #3", "", "", 1, isFolder, 0)
        itemQueries?.markAllAsRead()

        assertEquals(itemQueries?.selectAllByFeedIdAndType(feedId, isFolder)?.executeAsOne()?.isUnread, 0L)
    }

    @Test
    fun testDecreaseUnreadCountAsRead() {
        Database.clear()

        val firstFeedId = 1L
        val isFolder = false.toLong()

        val feedQueries = Database.getFeedQueries()
        feedQueries?.insert(firstFeedId, "Kotlin", "", 4, 0, isFolder)
        feedQueries?.decreaseUnreadCount(firstFeedId, isFolder)

        assertEquals(feedQueries?.selectAllByTitle()?.executeAsOne()?.unreadCount, 3L)
    }

    @Test
    fun testFeedSortingSettings() {
        Database.clear()

        val feedQueries = Database.getFeedQueries()
        feedQueries?.insert(1, "Feed", "", 4, 0, 0)
        feedQueries?.insert(2, "AwesomeFolder", "", 0, 0, 1)

        Database.getUserQueries()?.updateFolderTop(0L)
        Database.getUserQueries()?.updateSorting(Database.SORT_TITLE)

        assertEquals("AwesomeFolder", Database.getFeeds()[0].title)

        Database.getUserQueries()?.updateSorting(Database.SORT_UNREADCOUNT)

        assertEquals("Feed", Database.getFeeds()[0].title)

        Database.getUserQueries()?.updateSorting(Database.SORT_UNREADCOUNT)
        Database.getUserQueries()?.updateFolderTop(1L)

        assertEquals("AwesomeFolder", Database.getFeeds()[0].title)
    }

    @Test
    fun testFeedUpdateCache() {
        Database.clear()

        val userQueries = Database.getUserQueries()

        //userQueries?.updateFeedCache(DateTime.now().unixMillisLong)

        assertTrue { Database.getUser()?.isFeedCacheOutdated() == false }

        //userQueries?.updateFeedCache(DateTime.now().unixMillisLong - 6.minutes())

        assertTrue { Database.getUser()?.isFeedCacheOutdated() == true }
    }
}