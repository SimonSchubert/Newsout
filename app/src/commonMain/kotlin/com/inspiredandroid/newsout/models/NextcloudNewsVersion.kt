package com.inspiredandroid.newsout.models

import kotlinx.serialization.Serializable

@Serializable
data class NextcloudNewsVersion(
    var version: String? = null
)