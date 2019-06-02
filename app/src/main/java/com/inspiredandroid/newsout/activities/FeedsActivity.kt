package com.inspiredandroid.newsout.activities

import android.accounts.AccountManager
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

    private val adapter = FeedsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)

        fab.setOnClickListener { _ ->
            Api.markAllAsRead {
                if (isThere()) {
                    updateAdapterAndHideLoading(it)
                    updateFab()
                }
            }
        }
        swiperefresh.setOnRefreshListener(this)

        val layoutManager = GridLayoutManager(this, recyclerView.calculateNumberOfColumns())
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    FeedsAdapter.TYPE_FEED -> 1
                    FeedsAdapter.TYPE_HEADER -> recyclerView.calculateNumberOfColumns()
                    else -> 1
                }
            }
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        updateFab()
    }

    override fun onResume() {
        super.onResume()

        if (!Api.isCredentialsAvailable()) {
            val accountManager = AccountManager.get(this)
            accountManager.getAccountsByType(BuildConfig.APPLICATION_ID).forEach {
                val password = accountManager.getPassword(it)
                val url = accountManager.getUserData(it, LoginActivity.EXTRA_BASE_URL)
                Api.setCredentials(url, it.name, password)
            }
        }

        adapter.updateFeeds(Database.getFeeds())
        if (Database.getUser()?.isStarredCacheOutdated() == true) {
            Api.getStarredItems({}, {})
        }
        if (Database.getUser()?.isFeedCacheOutdated() == true) {
            showLoading()
            fetchFeeds()
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
        fetchFeeds()
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

    override fun onClickUnread() {
        val intent = Intent(this, ItemsActivity::class.java)
        intent.putExtra(ItemsActivity.KEY_TITLE, "Unread")
        intent.putExtra(ItemsActivity.KEY_TYPE, Database.TYPE_UNREAD)
        startActivity(intent)
    }

    override fun onClickStarred() {
        val intent = Intent(this, ItemsActivity::class.java)
        intent.putExtra(ItemsActivity.KEY_TITLE, "Starred")
        intent.putExtra(ItemsActivity.KEY_TYPE, Database.TYPE_STARRED)
        startActivity(intent)
    }

    override fun onAddFeed(url: String) {
        showLoading()
        Api.createFeed(url, 0, {
            if (isThere()) {
                updateAdapterAndHideLoading(Database.getFeeds())
                updateFab()
            }
        }, {
            if (isThere()) {
                hideLoading()
                val dialog = AddFeedDialog.getInstance(url)
                dialog.show(supportFragmentManager, "TAG")
            }
        })
    }

    override fun onEditFeed(id: Long, title: String, isFolder: Boolean) {
        showLoading()
        if (isFolder) {
            renameFolder(id, title)
        } else {
            renameFeed(id, title)
        }
    }

    override fun onDeleteFeed(id: Long, title: String, isFolder: Boolean) {
        showLoading()
        if (isFolder) {
            deleteFolder(id)
        } else {
            deleteFeed(id)
        }
    }

    override fun onSortingChange() {
        adapter.updateFeeds(Database.getFeeds())
    }

    private fun fetchFeeds() {
        Api.getFeeds({
            if (isThere()) {
                updateAdapterAndHideLoading(it)
                updateFab()
            }
        }, {
            if (isThere()) {
                hideLoading()
            }
        }, {
            if (isThere()) {
                val accountManager = AccountManager.get(this)
                accountManager.getAccountsByType(BuildConfig.APPLICATION_ID).forEach {
                    accountManager.removeAccountExplicitly(it)
                }

                Database.clear()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun updateAdapterAndHideLoading(feeds: List<Feed>) {
        adapter.updateFeeds(feeds)
        swiperefresh?.isRefreshing = false
    }

    private fun renameFeed(id: Long, title: String) {
        Api.renameFeed(id, title, {
            if (isThere()) {
                updateAdapterAndHideLoading(Database.getFeeds())
            }
        }, {
            if (isThere()) {
                hideLoading()
                val dialog = EditFeedDialog.getInstance(id, title, false, true)
                dialog.show(supportFragmentManager, "TAG")
            }
        })
    }

    private fun renameFolder(id: Long, title: String) {
        Api.renameFolder(id, title, {
            if (isThere()) {
                updateAdapterAndHideLoading(Database.getFeeds())
            }
        }, {
            if (isThere()) {
                hideLoading()
                val dialog = EditFeedDialog.getInstance(id, title, true, true)
                dialog.show(supportFragmentManager, "TAG")
            }
        })
    }

    private fun deleteFolder(id: Long) {
        Api.deleteFolder(id, {
            if (isThere()) {
                updateAdapterAndHideLoading(Database.getFeeds())
                updateFab()
            }
        }, {
            if (isThere()) {
                hideLoading()
            }
        })
    }

    private fun deleteFeed(id: Long) {
        Api.deleteFeed(id, {
            if (isThere()) {
                updateAdapterAndHideLoading(Database.getFeeds())
                updateFab()
            }
        }, {
            if (isThere()) {
                hideLoading()
            }
        })
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
}