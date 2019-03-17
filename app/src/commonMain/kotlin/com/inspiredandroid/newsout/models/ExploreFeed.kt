package com.inspiredandroid.newsout.models

import kotlinx.serialization.Serializable

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
@Serializable
data class ExploreFeed(
    var title: String,
    var url: String
) {
    companion object {
        val exploreFeeds = listOf(
            ExploreFeed("Nextcloud", "https://nextcloud.com/blogfeed"),
            ExploreFeed("Opensource.com", "https://opensource.com/feed"),
            ExploreFeed("Electrek", "electrek.co/feed/"),
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