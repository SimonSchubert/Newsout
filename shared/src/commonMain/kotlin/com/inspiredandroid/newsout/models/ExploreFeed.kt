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
            ExploreFeed("Hacker News", "https://news.ycombinator.com/rss"),
            ExploreFeed("TechCrunch", "https://techcrunch.com/feed"),
            ExploreFeed("Opensource.com", "https://opensource.com/feed"),
            ExploreFeed("Electrek", "https://electrek.co/feed"),
            ExploreFeed("OpenAI", "https://openai.com/blog/rss"),
            ExploreFeed("SpaceX", "https://www.spacex.com/news.xml"),
            ExploreFeed("NASA", "https://www.nasa.gov/rss/dyn/breaking_news.rss"),
            ExploreFeed("Nextcloud", "https://nextcloud.com/blogfeed"),
            ExploreFeed("Bitwarden", "https://blog.bitwarden.com/feed"),
            ExploreFeed("Mycroft AI", "https://mycroft.ai/feed"),
            ExploreFeed("Mozilla", "https://blog.mozilla.org/feed"),
            ExploreFeed("Purism", "https://puri.sm/feed"),
            ExploreFeed("EurekAlert!", "https://www.eurekalert.org/rss.xml"),
            ExploreFeed("Steam", "https://store.steampowered.com/feeds/news.xml"),
            ExploreFeed("Github", "https://github.blog/feed/atom"),
            ExploreFeed("Kotlin language", "https://blog.jetbrains.com/kotlin/feed"),
            ExploreFeed("Rust language", "https://blog.rust-lang.org/feed.xml"),
            ExploreFeed("Swift language", "https://swift.org/atom.xml?format=xml"),
            ExploreFeed("Inhabitat", "https://inhabitat.com/feed"),
            ExploreFeed("Joe Rogan Podcast", "http://podcasts.joerogan.net/feed")
        )
    }
}