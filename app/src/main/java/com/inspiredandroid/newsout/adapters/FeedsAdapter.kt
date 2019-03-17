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

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class FeedsAdapter(var feeds: MutableList<Feed>, val listener: OnFeedClickInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NORMAL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.row_feed, parent, false)
                ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.row_feed_header, parent, false)
                HeaderViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (feeds.count() > 0) {
            feeds.count()
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (feeds.count() > 0) {
            (holder as? ViewHolder)?.bind(feeds[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (feeds.count() > 0) {
            TYPE_NORMAL
        } else {
            TYPE_HEADER
        }
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
                Glide.with(containerView.context).load(feed.faviconUrl).placeholder(R.drawable.ic_icons8_rss)
                    .into(feedIcon)
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
                listener.onClickFeed(feed.id, feed.title, feed.isFolder)
            }
            containerView.setOnLongClickListener {
                listener.onLongClickFeed(feed.id, feed.title, feed.isFolder.toBoolean())
                true
            }
        }
    }

    inner class HeaderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_HEADER = 1
    }
}