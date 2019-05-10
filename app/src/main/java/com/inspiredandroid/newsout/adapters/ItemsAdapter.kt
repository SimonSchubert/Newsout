package com.inspiredandroid.newsout.adapters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
class ItemsAdapter(private var items: List<Item>, private val listener: OnItemClickInterface) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    internal val unreadMap: MutableMap<Long, Boolean> = mutableMapOf()
    internal val starredMap: MutableMap<Long, Boolean> = mutableMapOf()
    internal var query = ""

    init {
        updateItems(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Update adapter data and notify change
     */
    fun updateItems(items: List<Item>) {
        this.items = items
        this.items.forEach {
            unreadMap[it.id] = it.isUnread.toBoolean()
            starredMap[it.id] = it.isStarred.toBoolean()
        }
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        internal var id = 0L
        private var guidHash = ""
        private var feedId = 0L
        internal var isUnread = false
        private var isStarred = false
        private var isFolder = false

        internal fun bind(item: Item) {
            id = item.id
            feedId = item.feedId
            guidHash = item.guidHash
            isFolder = item.isFolder.toBoolean()
            isUnread = unreadMap[item.id] ?: false
            isStarred = starredMap[item.id] ?: false

            val spannable = when {
                isStarred -> getTitleWithIcon(item, R.drawable.ic_icons8_star)
                isUnread -> getTitleWithIcon(item, R.drawable.ic_icons8_appointment_reminders)
                else -> SpannableString(item.title)
            }

            if(query.isNotEmpty()) {
                var start = spannable.toString().toLowerCase().indexOf(query)
                while (start >= 0) {
                    val spanStart = Math.min(start, spannable.length)
                    val spanEnd = Math.min(start + query.length, spannable.length)

                    if (spanStart == -1 || spanEnd == -1) {
                        break
                    }

                    spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(containerView.context, R.color.turquoise)), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    start = spannable.toString().toLowerCase().indexOf(query, spanEnd)
                }
            }

            title.text = spannable

            if (item.imageUrl.isNotEmpty()) {
                Glide
                    .with(containerView.context)
                    .load(item.imageUrl)
                    .into(imageView)
                imageView.init(item.imageUrl)
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.GONE
            }

            containerView.setOnClickListener {
                listener.onClickItem(item.url)
                markAsRead()
            }
            containerView.setOnLongClickListener {
                if (isStarred) {
                    markAsUnstarred()
                } else {
                    markAsStarred()
                }
                true
            }
        }

        private fun getTitleWithIcon(item: Item, resId: Int): SpannableString {
            val span = SpannableString("  " + item.title)
            val drawable = ContextCompat.getDrawable(
                containerView.context, resId
            )
            drawable?.let {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                span.setSpan(ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            return span
        }

        internal fun markAsRead() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                Database.getItemQueries()?.markItemAsRead(id)
                Database.getFeedQueries()
                    ?.decreaseUnreadCount(feedId, isFolder.toLong())
                Api.markItemAsRead(id)
                unreadMap[id] = false
                notifyItemChanged(position)
            }
        }

        private fun markAsStarred() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                Database.getItemQueries()?.markItemAsStarred(id)
                Api.markItemAsStarred(feedId, guidHash)
                starredMap[id] = true
                notifyItemChanged(position)
            }
        }

        private fun markAsUnstarred() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                Database.getItemQueries()?.markItemAsUnstarred(id)
                Api.markItemAsUnstarred(feedId, guidHash)
                starredMap[id] = false
                notifyItemChanged(position)
            }
        }
    }
}
