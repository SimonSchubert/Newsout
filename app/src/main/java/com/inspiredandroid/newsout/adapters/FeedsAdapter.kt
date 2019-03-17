package com.inspiredandroid.newsout.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspiredandroid.newsout.Feed
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.callbacks.OnFeedClickInterface
import com.inspiredandroid.newsout.toBoolean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_feed.*

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
class FeedsAdapter(var feeds: MutableList<Feed>, val listener: OnFeedClickInterface) :
    RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_feed, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feeds.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position])
    }

    fun updateFeeds(nextcloudNewsFeed: List<Feed>) {
        feeds = nextcloudNewsFeed.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(feed: Feed) {
            feedTitle.text = feed.title

            if (feed.isFolder.toBoolean()) {
                Glide.with(containerView.context).load(R.drawable.ic_folder_black_24dp)
                    .placeholder(R.drawable.ic_folder_black_24dp)
                    .into(feedIcon)
            } else {
                Glide.with(containerView.context).load(feed.faviconUrl).placeholder(R.drawable.ic_icons8_rss).into(feedIcon)
            }

            if (feed.unreadCount > 0L) {
                feedTitle.setTypeface(null, Typeface.BOLD)
                feedUnreadCount.text = feed.unreadCount.toString()
                feedUnreadCount.visibility = View.VISIBLE
            } else {
                feedTitle.setTypeface(null, Typeface.NORMAL)
                feedUnreadCount.visibility = View.GONE
            }

            containerView.setOnClickListener {
                listener.onClickFeed(
                    feed.id, feed.title, feed.isFolder
                )
            }
            containerView.setOnLongClickListener {
                listener.onLongClickFeed(feed.id, feed.title, feed.isFolder.toBoolean())
                true
            }
        }
    }
}