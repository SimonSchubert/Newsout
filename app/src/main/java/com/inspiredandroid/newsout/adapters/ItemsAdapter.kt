package com.inspiredandroid.newsout.adapters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspiredandroid.newsout.*
import com.inspiredandroid.newsout.callbacks.OnItemClickInterface
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_item.*

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class ItemsAdapter(private var feeds: List<Item>, private val listener: OnItemClickInterface) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    internal val unreadMap: MutableMap<Long, Boolean> = mutableMapOf()

    init {
        updateItems(feeds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feeds.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position])
    }

    /**
     * Update adapter data and notify change
     */
    fun updateItems(items: List<Item>) {
        feeds = items
        feeds.forEach {
            unreadMap[it.id] = it.isUnread.toBoolean()
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        internal var id = 0L
        private var feedId = 0L
        internal var isUndread = false
        private var isFolder = false

        internal fun bind(feed: Item) {

            id = feed.id
            feedId = feed.feedId
            isFolder = feed.isFolder.toBoolean()
            isUndread = unreadMap[feed.id] ?: false

            if (isUndread) {
                val span = SpannableString("  " + feed.title)
                val drawable = ContextCompat.getDrawable(
                    containerView.context, R.drawable.ic_icons8_appointment_reminders
                )
                drawable?.let {
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    span.setSpan(ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
                title.text = span
            } else {
                title.text = feed.title
            }

            if (feed.imageUrl.isNotEmpty()) {
                Glide
                    .with(containerView.context)
                    .load(feed.imageUrl)
                    .into(imageView)
                imageView.init(feed.imageUrl)
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.GONE
            }

            containerView.setOnClickListener {
                listener.onClickItem(feed.url)
                markAsRead()
            }
        }

        internal fun markAsRead() {
            Database.getItemQueries()?.markItemAsRead(id)
            Database.getFeedQueries()
                ?.decreaseUnreadCount(feedId, isFolder.toLong())
            Api.markItemAsRead(id)
            unreadMap[id] = false
            notifyItemChanged(adapterPosition)
        }
    }
}
