package com.inspiredandroid.newsout.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.inspiredandroid.newsout.callbacks.OnAddFeedInterface
import com.inspiredandroid.newsout.models.ExploreFeed

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class ExploreFeedsDialog : DialogFragment() {

    private var callback: OnAddFeedInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = activity as? OnAddFeedInterface
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builderSingle = AlertDialog.Builder(requireContext())
        builderSingle.setTitle("Add feed")

        val arrayAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1)

        arrayAdapter.addAll(ExploreFeed.exploreFeeds.map { it.title })

        builderSingle.setNegativeButton(
            "cancel"
        ) { dialog, which -> dialog.dismiss() }

        builderSingle.setAdapter(arrayAdapter) { dialog, which ->
            callback?.onAddFeed(ExploreFeed.exploreFeeds[which].url)
        }
        return builderSingle.create()
    }

    companion object {

        internal fun getInstance(): ExploreFeedsDialog {
            return ExploreFeedsDialog()
        }
    }
}