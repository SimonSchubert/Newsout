package com.inspiredandroid.newsout.adapters

// import com.squareup.picasso.Picasso
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
class ItemsAdapter(var feeds: List<Item>, val listener: OnItemClickInterface) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    val unreadMap: MutableMap<Long, Boolean> = mutableMapOf()

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

        fun bind(feed: Item) {

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
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.GONE
            }

            containerView.setOnClickListener {
                listener.onClickItem(feed.url)
                markAsRead()
            }
        }

        fun markAsRead() {
            Database.getItemQueries()?.markItemAsRead(id)
            Database.getFeedQueries()
                ?.decreaseUnreadCount(feedId, isFolder.toLong())
            Api.markAsRead(id)
            unreadMap[id] = false
            notifyItemChanged(adapterPosition)
        }
    }
}
