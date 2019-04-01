package com.inspiredandroid.newsout.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.callbacks.OnEditFeedInterface

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class EditFeedDialog : DialogFragment() {

    private var callback: OnEditFeedInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = activity as? OnEditFeedInterface
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_feed, null)

        var title = arguments?.getString(KEY_TITLE, "") ?: ""
        val id = arguments?.getLong(KEY_ID, 0L) ?: 0L
        val error = arguments?.getBoolean(KEY_ERROR, false) ?: false
        val isFolder = arguments?.getBoolean(KEY_ISFOLDER, false) ?: false
        if (error) {
            view.findViewById<EditText>(R.id.editText).error = "Something went wrong"
        }
        view.findViewById<EditText>(R.id.editText).setText(title)

        return AlertDialog.Builder(requireContext())
            .setTitle(
                if (isFolder) {
                    "Edit folder"
                } else {
                    "Edit feed"
                }
            )
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                title = view.findViewById<EditText>(R.id.editText).text.toString()
                callback?.onEditFeed(id, title, isFolder)
            }
            .setNegativeButton("Delete") { _, _ ->
                callback?.onDeleteFeed(id, title, isFolder)
            }
            .create()
    }

    companion object {

        internal fun getInstance(id: Long, url: String, isFolder: Boolean, error: Boolean = false): EditFeedDialog {
            val dialog = EditFeedDialog()
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, url)
            bundle.putLong(KEY_ID, id)
            bundle.putBoolean(KEY_ERROR, error)
            bundle.putBoolean(KEY_ISFOLDER, isFolder)
            dialog.arguments = bundle
            return dialog
        }

        const val KEY_TITLE = "KEY_TITLE"
        const val KEY_ID = "KEY_ID"
        const val KEY_ERROR = "KEY_ERROR"
        const val KEY_ISFOLDER = "KEY_ISFOLDER"
    }
}