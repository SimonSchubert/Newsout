package com.inspiredandroid.purenews.models

import kotlinx.serialization.Serializable

@Serializable
data class NextcloudNewsFeed(
    val id: Long = -1,
    val url: String = "",
    val title: String = "",
    val folderId: Long = -1,
    val faviconLink: String = "",
    val unreadCount: Long = -1,
    val ordering: Long = -1
)