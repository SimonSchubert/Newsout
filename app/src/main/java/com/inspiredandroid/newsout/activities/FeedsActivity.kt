package com.inspiredandroid.newsout.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inspiredandroid.newsout.*
import com.inspiredandroid.newsout.adapters.FeedsAdapter
import com.inspiredandroid.newsout.callbacks.OnAddFeedInterface
import com.inspiredandroid.newsout.callbacks.OnEditFeedInterface
import com.inspiredandroid.newsout.callbacks.OnFeedClickInterface
import com.inspiredandroid.newsout.callbacks.OnSortingChangeInterface
import com.inspiredandroid.newsout.dialogs.AddFeedDialog
import com.inspiredandroid.newsout.dialogs.EditFeedDialog
import com.inspiredandroid.newsout.dialogs.SettingsDialog
import kotlinx.android.synthetic.main.activity_feeds.*
import kotlinx.android.synthetic.main.content_feeds.*

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class FeedsActivity : AppCompatActivity(), OnFeedClickInterface, SwipeRefreshLayout.OnRefreshListener,
    OnSortingChangeInterface, OnAddFeedInterface, OnEditFeedInterface {

    private val adapter = FeedsAdapter(Database.getFeeds(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)

        fab.setOnClickListener { view ->
            Api.markAllAsRead {
                updateAdapterAndHideLoading(it)
                updateFab()
            }
        }
        swiperefresh.setOnRefreshListener(this)

        val layoutManager = GridLayoutManager(this, calculateNumberOfColumns())
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    FeedsAdapter.TYPE_NORMAL -> 1
                    FeedsAdapter.TYPE_HEADER -> calculateNumberOfColumns()
                    else -> -1
                }
            }
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        updateFab()
    }

    override fun onResume() {
        super.onResume()

        adapter.updateFeeds(Database.getFeeds())
        if (Database.getUser()?.isCacheOutdated() ?: false) {
            showLoading()
            Api.feeds({
                updateAdapterAndHideLoading(it)
                updateFab()
            }, {
                hideLoading()
            })
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
        Api.feeds({
            updateAdapterAndHideLoading(it)
            updateFab()
        }, {
            hideLoading()
        })
    }

    override fun onClickFeed(id: Long, title: String, type: Long) {
        val intent = Intent(this, ItemsActivity::class.java)
        intent.putExtra(ItemsActivity.KEY_ID, id)
        intent.putExtra(ItemsActivity.KEY_TITLE, title)
        intent.putExtra(ItemsActivity.KEY_TYPE, type)
        startActivity(intent)
    }

    override fun onLongClickFeed(id: Long, title: String, isFolder: Boolean) {
        val dialog = EditFeedDialog.getInstance(id, title, isFolder)
        dialog.show(supportFragmentManager, "TAG")
    }

    override fun onAddFeed(url: String) {
        showLoading()
        Api.createFeed(url, 0, {
            updateAdapterAndHideLoading(Database.getFeeds())
            updateFab()
        }, {
            hideLoading()
            val dialog = AddFeedDialog.getInstance(url)
            dialog.show(supportFragmentManager, "TAG")
        })
    }

    override fun onEditFeed(id: Long, title: String, isFolder: Boolean) {
        showLoading()
        if (isFolder) {
            Api.renameFolder(id, title, {
                updateAdapterAndHideLoading(Database.getFeeds())
            }, {
                hideLoading()
                val dialog = EditFeedDialog.getInstance(id, title, isFolder, true)
                dialog.show(supportFragmentManager, "TAG")
            })
        } else {
            Api.renameFeed(id, title, {
                updateAdapterAndHideLoading(Database.getFeeds())
            }, {
                hideLoading()
                val dialog = EditFeedDialog.getInstance(id, title, isFolder, true)
                dialog.show(supportFragmentManager, "TAG")
            })
        }
    }

    override fun onDeleteFeed(id: Long, title: String, isFolder: Boolean) {
        showLoading()
        if (isFolder) {
            Api.deleteFolder(id, {
                updateAdapterAndHideLoading(Database.getFeeds())
                updateFab()
            }, {
                hideLoading()
            })
        } else {
            Api.deleteFeed(id, {
                updateAdapterAndHideLoading(Database.getFeeds())
                updateFab()
            }, {
                hideLoading()
            })
        }
    }

    override fun onSortingChange() {
        adapter.updateFeeds(Database.getFeeds())
    }

    private fun updateAdapterAndHideLoading(feeds: List<Feed>) {
        adapter.updateFeeds(feeds)
        swiperefresh?.isRefreshing = false
    }

    private fun hideLoading() {
        swiperefresh?.isRefreshing = false
    }

    private fun showLoading() {
        swiperefresh?.isRefreshing = true
    }

    private fun updateFab() {
        if (adapter.feeds.any { it.unreadCount > 0 }) {
            fab.show()
        } else {
            fab.hide()
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