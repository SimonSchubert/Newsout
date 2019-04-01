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
            ExploreFeed("Hacker News", "https://news.ycombinator.com/rss"),
            ExploreFeed("Opensource.com", "https://opensource.com/feed"),
            ExploreFeed("Mycroft AI", "https://mycroft.ai/feed/"),
            ExploreFeed("Mozilla", "https://blog.mozilla.org/feed/"),
            ExploreFeed("Electrek", "https://electrek.co/feed/"),
            ExploreFeed("OpenAI", "https://openai.com/blog/rss/"),
            ExploreFeed("TechCrunch", "https://techcrunch.com/feed/"),
            ExploreFeed("SpaceX", "https://www.spacex.com/news.xml"),
            ExploreFeed("Nasa", "https://www.nasa.gov/rss/dyn/breaking_news.rss"),
            ExploreFeed("Bitwarden", "https://blog.bitwarden.com/feed"),
            ExploreFeed("Purism", "https://puri.sm/feed/"),
            ExploreFeed("Steam", "https://store.steampowered.com/feeds/news.xml"),
            ExploreFeed("Joe Rogan Podcast", "http://podcasts.joerogan.net/feed")
        )
    }
}