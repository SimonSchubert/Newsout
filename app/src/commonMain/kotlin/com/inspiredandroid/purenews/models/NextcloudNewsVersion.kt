package com.inspiredandroid.purenews.models

import kotlinx.serialization.Serializable

@Serializable
data class NextcloudNewsVersion(
    var version: String? = null
)