package com.inspiredandroid.newsout.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inspiredandroid.newsout.ApplicationApi
import com.inspiredandroid.newsout.Database
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.adapters.FeedsAdapter
import com.inspiredandroid.newsout.callbacks.OnAddFeedInterface
import com.inspiredandroid.newsout.callbacks.OnListClickInterface
import com.inspiredandroid.newsout.callbacks.OnSortingChangeInterface
import com.inspiredandroid.newsout.dialogs.AddFeedDialog
import com.inspiredandroid.newsout.dialogs.SettingsDialog
import kotlinx.android.synthetic.main.activity_feeds.*
import kotlinx.android.synthetic.main.content_feeds.*

class FeedsActivity : AppCompatActivity(), OnListClickInterface, SwipeRefreshLayout.OnRefreshListener,
    OnSortingChangeInterface, OnAddFeedInterface {

    private val adapter = FeedsAdapter(Database.getFeeds(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)

        fab.setOnClickListener { view ->
            ApplicationApi.markAllAsRead {
                adapter.updateFeeds(it)
            }
        }

        swiperefresh.setOnRefreshListener(this)
        swiperefresh.isRefreshing = true

        recyclerView.layoutManager = GridLayoutManager(this, calculateNumberOfColumns())
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        adapter.updateFeeds(Database.getFeeds())
        ApplicationApi.feeds {
            adapter.updateFeeds(it)
            swiperefresh?.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feeds, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when {
            item?.itemId == R.id.action_settings -> {
                val dialog = SettingsDialog.getInstance()
                dialog.show(supportFragmentManager, "TAG")
                return true
            }
            item?.itemId == R.id.action_add -> {
                val dialog = AddFeedDialog.getInstance()
                dialog.show(supportFragmentManager, "TAG")
            }
        }
        return false
    }

    override fun onRefresh() {
        ApplicationApi.feeds {
            adapter.updateFeeds(it)
            swiperefresh?.isRefreshing = false
        }
    }

    override fun onClickList(id: Long, title: String, type: Long) {
        val intent = Intent(this, ItemsActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("type", type)
        startActivity(intent)
    }

    override fun onAddFeed(url: String) {
        swiperefresh?.isRefreshing = true
        ApplicationApi.createFeed(url, 0) {
            adapter.updateFeeds(Database.getFeeds())
            swiperefresh?.isRefreshing = false
        }
    }

    override fun onSortingChange() {
        adapter.updateFeeds(Database.getFeeds())
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
