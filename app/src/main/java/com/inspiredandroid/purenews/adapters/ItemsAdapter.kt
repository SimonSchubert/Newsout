package com.inspiredandroid.purenews.adapters

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspiredandroid.purenews.Item
import com.inspiredandroid.purenews.R
import com.inspiredandroid.purenews.toBoolean
// import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_item.*

class ItemsAdapter(var feeds: List<Item>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

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

    fun markAsRead(position: Int, id: Long) {
        unreadMap[id] = false
        notifyItemChanged(position)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        internal var id = 0L
        internal var feedId = 0L
        internal var isUndread = false
        internal var isFolder = false

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
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(feed.url))
                containerView.context.startActivity(browserIntent)
            }
        }
    }

    companion object {
        const val SORT_ORDERING = 1
        const val SORT_ALPHABETICALLY = 2
        const val SORT_UNREADCOUNT = 3
    }
}
