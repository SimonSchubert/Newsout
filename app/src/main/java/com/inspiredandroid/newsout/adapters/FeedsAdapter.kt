package com.inspiredandroid.newsout.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspiredandroid.newsout.Database
import com.inspiredandroid.newsout.Feed
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.callbacks.OnFeedClickInterface
import com.inspiredandroid.newsout.toBoolean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_feed.*

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class FeedsAdapter(internal var feeds: MutableList<Feed>, private val listener: OnFeedClickInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.row_feed_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.row_feed, parent, false)
                FeedViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (feeds.count() > 0) {
            feeds.count() + 2
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_FEED ->
                (holder as? FeedViewHolder)?.bindFeed(feeds[position - 2])
            TYPE_STARRED ->
                (holder as? FeedViewHolder)?.bindStarred()
            TYPE_UNREAD ->
                (holder as? FeedViewHolder)?.bindUnread()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (feeds.count() > 0) {
            when (position) {
                0 -> TYPE_STARRED
                1 -> TYPE_UNREAD
                else -> TYPE_FEED
            }
        } else {
            TYPE_HEADER
        }
    }

    internal fun updateFeeds(nextcloudNewsFeed: List<Feed>) {
        feeds = nextcloudNewsFeed.toMutableList()
        notifyDataSetChanged()
    }

    inner class FeedViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        internal fun bindFeed(feed: Feed) {
            feedTitle.text = feed.title

            if (feed.isFolder.toBoolean()) {
                Glide.with(containerView.context).load(R.drawable.ic_folder_black_24dp)
                    .placeholder(R.drawable.ic_folder_black_24dp)
                    .into(feedIcon)
            } else {
                Glide.with(containerView.context).load(feed.faviconUrl).placeholder(R.drawable.ic_icons8_rss)
                    .into(feedIcon)
            }

            updateCounter(feed.unreadCount)

            containerView.setOnClickListener {
                listener.onClickFeed(feed.id, feed.title, feed.isFolder)
            }
            containerView.setOnLongClickListener {
                listener.onLongClickFeed(feed.id, feed.title, feed.isFolder.toBoolean())
                true
            }
        }

        internal fun bindStarred() {
            feedTitle.text = "Starred"

            Glide.with(containerView.context).load(R.drawable.ic_icons8_star)
                .placeholder(R.drawable.ic_icons8_star)
                .into(feedIcon)

            updateCounter(Database.getTotalStarredCount())

            containerView.setOnClickListener {
                listener.onClickStarred()
            }
            containerView.setOnLongClickListener(null)
        }

        internal fun bindUnread() {
            feedTitle.text = "Unread"

            Glide.with(containerView.context).load(R.drawable.ic_icons8_visible)
                .placeholder(R.drawable.ic_icons8_visible)
                .into(feedIcon)

            updateCounter(Database.getTotalUnreadCount())

            containerView.setOnClickListener {
                listener.onClickUnread()
            }
            containerView.setOnLongClickListener(null)
        }

        private fun updateCounter(counter: Long) {
            if (counter > 0L) {
                feedTitle.setTypeface(null, Typeface.BOLD)
                feedUnreadCount.text = counter.toString()
                feedUnreadCount.visibility = View.VISIBLE
            } else {
                feedTitle.setTypeface(null, Typeface.NORMAL)
                feedUnreadCount.visibility = View.GONE
            }
        }
    }

    inner class HeaderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    companion object {
        const val TYPE_FEED = 0
        const val TYPE_HEADER = 1
        const val TYPE_STARRED = 2
        const val TYPE_UNREAD = 3
    }
}