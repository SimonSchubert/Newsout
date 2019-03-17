package com.inspiredandroid.newsout.models

import kotlinx.serialization.Serializable

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
@Serializable
data class NextcloudNewsVersion(
    var version: String? = null
)