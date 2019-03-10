package com.inspiredandroid.purenews.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inspiredandroid.purenews.*
import com.inspiredandroid.purenews.adapters.ItemsAdapter
import com.inspiredandroid.purenews.callbacks.OnAddFeedInterface
import com.inspiredandroid.purenews.dialogs.AddFeedDialog
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.content_feeds.*


class ItemsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, OnAddFeedInterface {

    private val adapter by lazy {
        ItemsAdapter(Database.getItems(id, type))
    }
    private val layoutManager by lazy {
        StaggeredGridLayoutManager(calculateNumberOfColumns(), StaggeredGridLayoutManager.VERTICAL)
    }
    var id: Long = 0
    var type: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getLongExtra("id", 0)
        type = intent.getLongExtra("type", 0L)
        title = intent.getStringExtra("title")

        fab.setOnClickListener { view ->
            if (type == 0L) {
                ApplicationApi.markFeedAsRead(id) {
                    adapter.updateItems(it)
                }
            } else {
                ApplicationApi.markFolderAsRead(id) {
                    adapter.updateItems(it)
                }
            }
        }

        swiperefresh.setOnRefreshListener(this)
        swiperefresh.isRefreshing = true

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpacingItemDecoration(4.px))
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val positions = layoutManager.findFirstVisibleItemPositions(null)
                positions.forEach {
                    val viewHolder = (recyclerView.findViewHolderForAdapterPosition(it) as ItemsAdapter.ViewHolder)
                    if (viewHolder.isUndread) {
                        adapter.markAsRead(it, viewHolder.id)
                        Database.getItemQueries()?.markItemAsRead(viewHolder.id)
                        Database.getFeedQueries()?.decreaseUnreadCount(viewHolder.feedId, viewHolder.isFolder.toLong())
                        ApplicationApi.markAsRead(viewHolder.id)
                    }
                }
            }
        }
        recyclerView.addOnScrollListener(listener)

        ApplicationApi.items(id, type) {
            adapter.updateItems(it)
            swiperefresh?.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (type == 1L) {
            menuInflater.inflate(R.menu.menu_items, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.action_add -> {
                val dialog = AddFeedDialog.getInstance()
                dialog.show(supportFragmentManager, "TAG")
            }
        }
        return true
    }

    override fun onRefresh() {
        ApplicationApi.items(id, type) {
            adapter.updateItems(it)
            swiperefresh?.isRefreshing = false
        }
    }

    override fun onAddFeed(url: String) {
        swiperefresh?.isRefreshing = true
        ApplicationApi.createFeed(url, id) {
            ApplicationApi.items(id, type) {
                adapter.updateItems(it)
                swiperefresh?.isRefreshing = false
            }
        }
    }

    private fun calculateNumberOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        var columns = (dpWidth / 300).toInt()
        if (columns < 1) {
            columns = 1
        }
        return columns
    }
}
