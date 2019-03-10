package com.inspiredandroid.newsout

import com.inspiredandroid.newsout.models.NextcloudNewsFeed
import com.inspiredandroid.newsout.models.NextcloudNewsFolder
import com.inspiredandroid.newsout.models.NextcloudNewsItem
import com.inspiredandroid.newsout.models.NextcloudNewsVersion
import com.squareup.sqldelight.db.SqlDriver
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
import kotlinx.serialization.list

internal expect val ApplicationDispatcher: CoroutineDispatcher
internal expect var sqlDriver: SqlDriver?
internal expect fun done(block: suspend CoroutineScope.() -> Unit)

object ApplicationApi {
    private val client = HttpClient()

    var credentials = ""
    var nextcloudUrl = ""
    private val baseUrl get() = "$nextcloudUrl/index.php/apps/news/api/v1-2"

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
            } catch (cause: Throwable) {
                done {
                    error()
                }
            }
        }
    }

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
            } catch (cause: Throwable) {
            }
        }
    }

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
            } catch (cause: Throwable) {
            }
        }
    }

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
            } catch (cause: Throwable) {
            }
        }
    }

    fun markAsRead(itemId: Long) {
        async {
            try {
                client.put {
                    url("$baseUrl/items/$itemId/read")
                    header("Authorization", "Basic $credentials")
                }
            } catch (cause: Throwable) {
            }
        }
    }

    fun feeds(callback: (List<Feed>) -> Unit) {
        folders { folders ->
            async {
                try {
                    val result: String = client.get {
                        url("$baseUrl/feeds")
                        header("Authorization", "Basic $credentials")
                    }
                    mergeNextcloudFeedsAndFolders(folders, result, true)

                    done { callback(Database.getFeeds()) }
                } catch (cause: Throwable) {
                }
            }
        }
    }

    private fun folders(callback: (List<NextcloudNewsFolder>) -> Unit) {
        async {
            try {
                val result: String = client.get {
                    url("$baseUrl/folders")
                    header("Authorization", "Basic $credentials")
                }

                val array = Json.nonstrict.parseJson(result).jsonObject.getArrayOrNull("folders")
                array?.let {
                    val list = Json.nonstrict.parse(NextcloudNewsFolder.serializer().list, it.jsonArray.toString())
                    done { callback(list) }
                }
            } catch (cause: Throwable) {
            }
        }
    }

    fun createFeed(url: String, folderId: Long, callback: () -> Unit) {
        folders { folders ->
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
                    mergeNextcloudFeedsAndFolders(folders, result, false)

                    done { callback() }
                } catch (cause: Throwable) {
                }
            }
        }
    }

    fun items(id: Long, type: Long, callback: (List<Item>) -> Unit) {
        async {
            try {
                val result: String = client.get {
                    url("$baseUrl/items?getRead=true&batchSize=-1&type=$type&id=$id")
                    header("Authorization", "Basic $credentials")
                }

                val array = Json.nonstrict.parseJson(result).jsonObject.getArrayOrNull("items")
                array?.let {
                    val list = Json.nonstrict.parse(NextcloudNewsItem.serializer().list, it.jsonArray.toString())

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
                }
            } catch (cause: Throwable) {
            }
        }
    }

    fun mergeNextcloudFeedsAndFolders(folders: List<NextcloudNewsFolder>, result: String, clear: Boolean) {
        val feedsArray = Json.nonstrict.parseJson(result).jsonObject.getArrayOrNull("feeds")
        feedsArray?.let {
            val list = Json.nonstrict.parse(NextcloudNewsFeed.serializer().list, it.jsonArray.toString())

            val feedQueries = Database.getFeedQueries()
            feedQueries?.let {
                if(clear) {
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