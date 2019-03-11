package com.inspiredandroid.newsout.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inspiredandroid.newsout.Api
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
class FeedsActivity : AppCompatActivity(), OnListClickInterface, SwipeRefreshLayout.OnRefreshListener,
    OnSortingChangeInterface, OnAddFeedInterface {

    private val adapter = FeedsAdapter(Database.getFeeds(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)

        fab.setOnClickListener { view ->
            Api.markAllAsRead {
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
        Api.feeds {
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
        Api.feeds {
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
        Api.createFeed(url, 0) {
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
