package com.inspiredandroid.newsout.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.callbacks.OnAddFeedInterface

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class AddFeedDialog : DialogFragment() {

    private var callback: OnAddFeedInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = activity as? OnAddFeedInterface
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_feed, null)

        var url = arguments?.getString(KEY_URL, "") ?: ""
        if (url.isNotEmpty()) {
            view.findViewById<EditText>(R.id.editText).setText(url)
            view.findViewById<EditText>(R.id.editText).error = "Something went wrong"
        }

        return AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Add feed")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                url = view.findViewById<EditText>(R.id.editText).text.toString()
                callback?.onAddFeed(url)
            }
            .setNeutralButton("Explore") { _, _ ->
                activity?.supportFragmentManager?.let {
                    val dialog = ExploreFeedsDialog.getInstance()
                    dialog.show(it, "TAG")
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
    }

    companion object {

        internal fun getInstance(url: String = ""): AddFeedDialog {
            val dialog = AddFeedDialog()
            val bundle = Bundle()
            bundle.putString(KEY_URL, url)
            dialog.arguments = bundle
            return dialog
        }

        const val KEY_URL = "KEY_URL"
    }
}