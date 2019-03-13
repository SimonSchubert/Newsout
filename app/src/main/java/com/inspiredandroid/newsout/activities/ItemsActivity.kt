package com.inspiredandroid.newsout.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inspiredandroid.newsout.*
import com.inspiredandroid.newsout.adapters.ItemsAdapter
import com.inspiredandroid.newsout.callbacks.OnAddFeedInterface
import com.inspiredandroid.newsout.callbacks.OnItemClickInterface
import com.inspiredandroid.newsout.dialogs.AddFeedDialog
import kotlinx.android.synthetic.main.activity_items.*
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
class ItemsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, OnAddFeedInterface,
    OnItemClickInterface {

    private val adapter by lazy {
        ItemsAdapter(Database.getItems(id, type), this)
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

        id = intent.getLongExtra(KEY_ID, 0)
        type = intent.getLongExtra(KEY_TYPE, 0L)
        title = intent.getStringExtra(KEY_TITLE)

        fab.setOnClickListener { view ->
            if (type == 0L) {
                Api.markFeedAsRead(id) {
                    adapter.updateItems(it)
                }
            } else {
                Api.markFolderAsRead(id) {
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
                positions.forEach { position ->
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as? ItemsAdapter.ViewHolder
                    viewHolder?.let {
                        if (viewHolder.isUndread) {
                            viewHolder.markAsRead()
                        }
                    }
                }
            }
        }
        recyclerView.addOnScrollListener(listener)

        Api.items(id, type, {
            adapter.updateItems(it)
            swiperefresh?.isRefreshing = false
        }, {
            swiperefresh?.isRefreshing = false
        })
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
        Api.items(id, type, {
            adapter.updateItems(it)
            swiperefresh?.isRefreshing = false
        }, {
            swiperefresh?.isRefreshing = false
        })
    }

    override fun onAddFeed(url: String) {
        swiperefresh?.isRefreshing = true
        Api.createFeed(url, id, {
            Api.items(id, type, {
                adapter.updateItems(it)
                swiperefresh?.isRefreshing = false
            }, {
                swiperefresh?.isRefreshing = false
            })
        }, {
            swiperefresh?.isRefreshing = false
        })
    }

    override fun onClickItem(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
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

    companion object {
        const val KEY_ID = "KEY_ID"
        const val KEY_TYPE = "KEY_TYPE"
        const val KEY_TITLE = "KEY_TITLE"
    }
}
