package com.inspiredandroid.purenews.adapters

// import com.squareup.picasso.Picasso
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspiredandroid.purenews.Feed
import com.inspiredandroid.purenews.R
import com.inspiredandroid.purenews.callbacks.OnListClickInterface
import com.inspiredandroid.purenews.models.NextcloudNewsFeed
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_feed.*

class FeedsAdapter(var feeds: MutableList<Feed>, val listener: OnListClickInterface) :
    RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    init {
    }

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

    fun addFeed(nextcloudNewsFeed: List<NextcloudNewsFeed>) {
        notifyDataSetChanged()
    }

    fun updateFeeds(nextcloudNewsFeed: List<Feed>) {
        feeds = nextcloudNewsFeed.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(feed: Feed) {
            feedTitle.text = feed.title

            if (feed.isFolder == 1L) {
                Glide.with(containerView.context).load(R.drawable.ic_folder_black_24dp)
                    .placeholder(R.drawable.ic_folder_black_24dp)
                    .into(feedIcon)
            } else {
                if (feed.faviconUrl.isNotEmpty()) {
                    Glide.with(containerView.context).load(feed.faviconUrl).into(feedIcon)
                }
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
                listener.onClickList(
                    feed.id, feed.title, feed.isFolder
                )
            }
        }
    }

    companion object {
        const val SORT_ORDERING = 1
        const val SORT_ALPHABETICALLY = 2
        const val SORT_UNREADCOUNT = 3
    }
}