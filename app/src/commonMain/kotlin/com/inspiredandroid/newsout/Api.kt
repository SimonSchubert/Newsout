package com.inspiredandroid.newsout

import com.inspiredandroid.newsout.models.NextcloudNewsFeed
import com.inspiredandroid.newsout.models.NextcloudNewsFolder
import com.inspiredandroid.newsout.models.NextcloudNewsItem
import com.inspiredandroid.newsout.models.NextcloudNewsVersion
import com.soywiz.klock.DateTime
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
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.list

internal expect val ApplicationDispatcher: CoroutineDispatcher
internal expect var sqlDriver: SqlDriver?
internal expect fun done(block: suspend CoroutineScope.() -> Unit)

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
object Api {
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

    @InternalAPI
    fun createAccount(
        url: String,
        email: String, password: String, success: () -> Unit, userExists: () -> Unit, error: () -> Unit
    ) {
        nextcloudUrl = url
        credentials = "$email:$password".encodeBase64()
        async {
            try {
                val response = client.get<HttpResponse>() {
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
                } catch (cause: Throwable) {
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
            } catch (cause: Throwable) {
                done { error() }
            }
        }
    }

    fun renameFolder(id: Long, title: String, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                val result: String = client.put {
                    url("$baseUrl/folders/$id")
                    header("Authorization", "Basic $credentials")
                    body = TextContent(
                        text = "{\"name\": \"$title\"}",
                        contentType = ContentType.Application.Json
                    )
                }

                Database.getFeedQueries()?.renameFolderFeed(title, id)

                done { callback() }
            } catch (cause: Throwable) {
                done { error() }
            }
        }
    }

    fun renameFeed(id: Long, title: String, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                val result: String = client.put {
                    url("$baseUrl/feeds/$id/rename")
                    header("Authorization", "Basic $credentials")
                    body = TextContent(
                        text = "{\"feedTitle\": \"$title\"}",
                        contentType = ContentType.Application.Json
                    )
                }

                Database.getFeedQueries()?.renameFeed(title, id)

                done { callback() }
            } catch (cause: Throwable) {
                done { error() }
            }
        }
    }

    fun deleteFeed(id: Long, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                val result: String = client.delete {
                    url("$baseUrl/feeds/$id")
                    header("Authorization", "Basic $credentials")
                }

                Database.getFeedQueries()?.deleteFeed(id)

                done { callback() }
            } catch (cause: Throwable) {
                done { error() }
            }
        }
    }

    fun deleteFolder(id: Long, callback: () -> Unit, error: () -> Unit) {
        async {
            try {
                val result: String = client.delete {
                    url("$baseUrl/folders/$id")
                    header("Authorization", "Basic $credentials")
                }

                Database.getFeedQueries()?.deleteFolderFeed(id)

                done { callback() }
            } catch (cause: Throwable) {
                done { error() }
            }
        }
    }

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
                } catch (cause: Throwable) {
                    done { error() }
                }
            }
        }, error)
    }

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
            } catch (cause: Throwable) {
                done { error() }
            }
        }
    }

    private fun mergeFeedsAndFolders(folders: List<NextcloudNewsFolder>, result: String, clear: Boolean) {
        val feedsArray = Json.nonstrict.parseJson(result).jsonObject.getArrayOrNull("feeds")
        feedsArray?.let {
            val list = Json.nonstrict.parse(NextcloudNewsFeed.serializer().list, it.jsonArray.toString())

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