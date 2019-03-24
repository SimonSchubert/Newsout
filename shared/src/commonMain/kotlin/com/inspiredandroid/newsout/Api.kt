package com.inspiredandroid.newsout

import com.inspiredandroid.newsout.models.NextcloudNewsFeed
import com.inspiredandroid.newsout.models.NextcloudNewsFolder
import com.inspiredandroid.newsout.models.NextcloudNewsItem
import com.inspiredandroid.newsout.models.NextcloudNewsVersion
import com.soywiz.klock.DateTime
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.list
import kotlin.native.concurrent.ThreadLocal

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
@ThreadLocal
object Api {
    @ThreadLocal
    private val client = HttpClient()
    @ThreadLocal
    private var credentials = ""
    @ThreadLocal
    private var nextcloudUrl = ""
    @ThreadLocal
    private val baseUrl get() = "$nextcloudUrl/index.php/apps/news/api/v1-2"

    init{

    }

    @InternalAPI
    fun setCredentials(url: String, email: String, password: String) {
        credentials = "$email:$password".encodeBase64()
        nextcloudUrl = url
    }

    @InternalAPI
    fun login(
        url: String,
        email: String,
        password: String,
        callback: (NextcloudNewsVersion) -> Unit,
        unauthorized: () -> Unit,
        error: () -> Unit
    ) {
        nextcloudUrl = url
        credentials = "$email:$password".encodeBase64()
        async {
            try {
                val response = client.get<HttpResponse> {
                    url("$baseUrl/version")
                    header("Authorization", "Basic $credentials")
                }

                if (response.status == HttpStatusCode.OK) {
                    val obj: NextcloudNewsVersion =
                        Json.nonstrict.parse(NextcloudNewsVersion.serializer(), response.readText())
                    done {
                        callback(obj)
                    }
                } else if (response.status == HttpStatusCode.Unauthorized) {
                    done {
                        unauthorized()
                    }
                }
            } catch (ignore: Throwable) {
                done {
                    error()
                }
            }
        }
    }

    @InternalAPI
    fun createAccount(
        url: String,
        email: String, password: String, success: () -> Unit, userExists: () -> Unit, error: () -> Unit
    ) {
        nextcloudUrl = url
        credentials = "$email:$password".encodeBase64()
        async {
            try {
                val response = client.get<HttpResponse> {
                    url("https://schubert-simon.de/newsout/nx_login.php?email=$email&password=$password")
                    header("OCS-APIRequest", "true")
                    header("Accept", "application/json")
                }

                if (response.status == HttpStatusCode.OK) {
                    val statusCode =
                        Json.nonstrict.parseJson(response.readText()).jsonObject.getObjectOrNull("ocs")?.getObjectOrNull(
                            "meta"
                        )?.get("statuscode")?.intOrNull ?: -1

                    done {
                        when (statusCode) {
                            100 -> success()
                            102 -> userExists()
                            else -> error()
                        }
                    }
                } else {
                    done {
                        error()
                    }
                }
            } catch (ignore: Throwable) {
                done {
                    error()
                }
            }
        }
    }

    /**
     * Marks feed as read
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-items-of-a-feed-as-read">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-items-of-a-feed-as-read</a>
     */
    fun markFeedAsRead(feedId: Long, callback: (List<Item>) -> Unit) {
        async {
            val feedQueries = Database.getFeedQueries()
            feedQueries?.markAsRead(feedId, false.toLong())

            val itemQueries = Database.getItemQueries()
            var maxId = 0L
            itemQueries?.let {
                maxId = it.maxId(feedId, false.toLong()).executeAsOneOrNull()?.MAX ?: 0L
                it.markAsRead(feedId, false.toLong())
                done {
                    callback(it.selectAllByFeedIdAndType(feedId, false.toLong()).executeAsList())
                }
            }

            try {
                client.put {
                    url("$baseUrl/feeds/$feedId/read?newestItemId=$maxId")
                    header("Authorization", "Basic $credentials")
                }
            } catch (ignore: Throwable) {
            }
        }
    }

    /**
     * Marks items of a folder as read
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-items-of-a-folder-as-read">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-items-of-a-folder-as-read</a>
     */
    fun markFolderAsRead(folderId: Long, callback: (List<Item>) -> Unit) {
        async {
            val feedQueries = Database.getFeedQueries()
            feedQueries?.markAsRead(folderId, true.toLong())

            val itemQueries = Database.getItemQueries()
            var maxId = 0L
            itemQueries?.let {
                it.markAsRead(folderId, true.toLong())
                maxId = it.maxId(folderId, true.toLong()).executeAsOneOrNull()?.MAX ?: 0L
                done {
                    callback(it.selectAllByFeedIdAndType(folderId, true.toLong()).executeAsList())
                }
            }

            try {
                client.put {
                    url("$baseUrl/folders/$folderId/read?newestItemId=$maxId")
                    header("Authorization", "Basic $credentials")
                }
            } catch (ignore: Throwable) {
            }
        }
    }

    /**
     * Marks all items as read
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-all-items-as-read">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-all-items-as-read</a>
     */
    fun markAllAsRead(callback: (List<Feed>) -> Unit) {
        async {
            val feedQueries = Database.getFeedQueries()
            feedQueries?.markAllAsRead()
            val itemQueries = Database.getItemQueries()
            itemQueries?.markAllAsRead()

            val maxId = itemQueries?.maxIdAll()?.executeAsOneOrNull()?.MAX ?: 0L
            done { callback(Database.getFeeds()) }

            try {
                client.put {
                    url("$baseUrl/items/read?newestItemId=$maxId")
                    header("Authorization", "Basic $credentials")
                }
            } catch (ignore: Throwable) {
            }
        }
    }

    /**
     * Marks item as read
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-an-item-as-read">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#mark-an-item-as-read</a>
     */
    fun markItemAsRead(itemId: Long) {
        async {
            try {
                client.put {
                    url("$baseUrl/items/$itemId/read")
                    header("Authorization", "Basic $credentials")
                }
            } catch (ignore: Throwable) {
            }
        }
    }

    /**
     * Gets feeds
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#get-all-feeds">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#get-all-feeds</a>
     */
    fun feeds(callback: (List<Feed>) -> Unit, error: () -> Unit) {
        folders({ folders ->
            async {
                try {
                    val result: String = client.get {
                        url("$baseUrl/feeds")
                        header("Authorization", "Basic $credentials")
                    }
                    mergeFeedsAndFolders(folders, result, true)

                    Database.getUserQueries()?.updateFeedCache(DateTime.now().unixMillisLong)

                    done { callback(Database.getFeeds()) }
                } catch (ignore: Throwable) {
                    done { error() }
                }
            }
        }, error)
    }

    private fun folders(callback: (List<NextcloudNewsFolder>) -> Unit, error: () -> Unit) {
        async {
            try {
                val result: String = client.get {
                    url("$baseUrl/folders")
                    header("Authorization", "Basic $credentials")
                }

                val array = Json.nonstrict.parseJson(result).jsonObject.getArrayOrNull("folders")

                if (array != null) {
                    val list = Json.nonstrict.parse(NextcloudNewsFolder.serializer().list, array.jsonArray.toString())
                    done { callback(list) }
                } else {
                    done { error() }
                }
            } catch (ignore: Throwable) {
                done { error() }
            }
        }
    }

    /**
     * Renames a folder
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#rename-a-folder">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#rename-a-folder</a>
     */
    fun renameFolder(id: Long, title: String, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                client.put<String> {
                    url("$baseUrl/folders/$id")
                    header("Authorization", "Basic $credentials")
                    body = TextContent(
                        text = "{\"name\": \"$title\"}",
                        contentType = ContentType.Application.Json
                    )
                }

                Database.getFeedQueries()?.renameFolderFeed(title, id)

                done { callback() }
            } catch (ignore: Throwable) {
                done { error() }
            }
        }
    }

    /**
     * Renames a feed
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#rename-a-feed">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#rename-a-feed</a>
     */
    fun renameFeed(id: Long, title: String, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                client.put<String> {
                    url("$baseUrl/feeds/$id/rename")
                    header("Authorization", "Basic $credentials")
                    body = TextContent(
                        text = "{\"feedTitle\": \"$title\"}",
                        contentType = ContentType.Application.Json
                    )
                }

                Database.getFeedQueries()?.renameFeed(title, id)

                done { callback() }
            } catch (ignore: Throwable) {
                done { error() }
            }
        }
    }

    /**
     * Deletes a feed
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#delete-a-feed">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#delete-a-feed</a>
     */
    fun deleteFeed(id: Long, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                client.delete<String> {
                    url("$baseUrl/feeds/$id")
                    header("Authorization", "Basic $credentials")
                }

                Database.getFeedQueries()?.deleteFeed(id)

                done { callback() }
            } catch (ignore: Throwable) {
                done { error() }
            }
        }
    }

    /**
     * Deletes a folder
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#delete-a-folder">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#delete-a-folder</a>
     */
    fun deleteFolder(id: Long, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                client.delete<String> {
                    url("$baseUrl/folders/$id")
                    header("Authorization", "Basic $credentials")
                }

                Database.getFeedQueries()?.deleteFolderFeed(id)

                done { callback() }
            } catch (ignore: Throwable) {
                done { error() }
            }
        }
    }

    /**
     * Creates a feed
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#create-a-feed">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#create-a-feed</a>
     */
    fun createFeed(url: String, folderId: Long, callback: () -> Unit, error: () -> Unit) {
        folders({ folders ->
            async {
                try {
                    val result: String = client.post {
                        url("$baseUrl/feeds")
                        header("Authorization", "Basic $credentials")
                        body = TextContent(
                            text = "{\"url\": \"$url\",\"folderId\": $folderId}",
                            contentType = ContentType.Application.Json
                        )
                    }

                    mergeFeedsAndFolders(folders, result, false)

                    done { callback() }
                } catch (ignore: Throwable) {
                    done { error() }
                }
            }
        }, error)
    }

    /**
     * Get items
     * @see <a href="https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#get-items">https://github.com/nextcloud/news/blob/master/docs/externalapi/Legacy.md#get-items</a>
     */
    fun items(id: Long, type: Long, offset: Boolean, callback: (List<Item>) -> Unit, error: () -> Unit) {
        async {
            try {
                var offsetString = ""
                if (offset) {
                    val minId = Database.getItemQueries()?.minId(id, type)?.executeAsOne()?.MIN ?: 0L
                    offsetString = "&offset=$minId"
                }
                val result: String = client.get {
                    url("$baseUrl/items?getRead=true&batchSize=20&type=$type&id=$id$offsetString")
                    header("Authorization", "Basic $credentials")
                }

                val array = Json.nonstrict.parseJson(result).jsonObject.getArrayOrNull("items")
                if (array != null) {
                    val list = Json.nonstrict.parse(NextcloudNewsItem.serializer().list, array.jsonArray.toString())

                    val itemQueries = Database.getItemQueries()
                    itemQueries?.let {
                        list.forEach {
                            itemQueries.insert(
                                it.id,
                                id,
                                it.title.trim(),
                                it.body.firstImageUrl(),
                                it.url,
                                it.unread.toLong(),
                                type
                            )
                        }

                        done {
                            callback(itemQueries.selectAllByFeedIdAndType(id, type).executeAsList())
                        }
                    }
                } else {
                    done { error() }
                }
            } catch (ignore: Throwable) {
                done { error() }
            }
        }
    }

    private fun mergeFeedsAndFolders(folders: List<NextcloudNewsFolder>, result: String, clear: Boolean) {
        val feedsArray = Json.nonstrict.parseJson(result).jsonObject.getArrayOrNull("feeds")
        feedsArray?.let {
            val list = Json.nonstrict.parse(NextcloudNewsFeed.serializer().list, it.jsonArray.toString())

            println("fetched feeds: " + list.count())

            val feedQueries = Database.getFeedQueries()
            feedQueries?.let {
                if (clear) {
                    feedQueries.clear()
                }
                list.forEach {
                    if (it.folderId == 0L) {
                        feedQueries.insert(
                            it.id,
                            it.title.trim(),
                            it.faviconLink,
                            it.unreadCount,
                            it.ordering,
                            0
                        )
                    } else {
                        val f = feedQueries.selectFolderFeedById(it.folderId).executeAsOneOrNull()
                        if (f != null) {
                            feedQueries.increaseUnreadCountForFolderFeed(it.unreadCount, it.folderId)
                        } else {
                            feedQueries.insert(
                                it.folderId,
                                folders.firstOrNull { folder -> folder.id == it.folderId }?.name?.trim()
                                    ?: "",
                                "",
                                it.unreadCount,
                                0,
                                1
                            )
                        }
                    }
                }
            }
        }
    }
}