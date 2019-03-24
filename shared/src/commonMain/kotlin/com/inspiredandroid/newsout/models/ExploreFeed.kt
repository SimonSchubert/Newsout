package com.inspiredandroid.newsout.models

import kotlinx.serialization.Serializable

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
@Serializable
data class ExploreFeed(
    var title: String,
    var url: String
) {
    companion object {
        val exploreFeeds = listOf(
            ExploreFeed("Nextcloud", "https://nextcloud.com/blogfeed"),
            ExploreFeed("Opensource.com", "https://opensource.com/feed"),
            ExploreFeed("Electrek", "https://electrek.co/feed/"),
            ExploreFeed("Bitwarden", "https://blog.bitwarden.com/"),
            ExploreFeed("Hacker News", "https://news.ycombinator.com/rss"),
            ExploreFeed("Purism", "https://puri.sm/feed/"),
            ExploreFeed("Mycroft AI", "https://mycroft.ai/blog/category/news/"),
            ExploreFeed("Steam", "https://store.steampowered.com/feeds/news.xml"),
            ExploreFeed("Mozilla", "https://blog.mozilla.org/feed/"),
            ExploreFeed("Joe Rogan Podcast", "http://podcasts.joerogan.net/feed")
        )
    }
}