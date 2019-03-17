package com.inspiredandroid.newsout.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.callbacks.OnEditFeedInterface

class EditFeedDialog : DialogFragment() {

    var callback: OnEditFeedInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = activity as? OnEditFeedInterface
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_feed, null)

        var title = arguments?.getString("title", "") ?: ""
        val id = arguments?.getLong("id", 0L) ?: 0L
        val error = arguments?.getBoolean("id", false) ?: false
        val isFolder = arguments?.getBoolean("isFolder", false) ?: false
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
            bundle.putString("title", url)
            bundle.putLong("id", id)
            bundle.putBoolean("error", error)
            bundle.putBoolean("isFolder", isFolder)
            dialog.arguments = bundle
            return dialog
        }
    }
}