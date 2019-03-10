package com.inspiredandroid.purenews.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.inspiredandroid.purenews.R
import com.inspiredandroid.purenews.callbacks.OnAddFeedInterface

class AddFeedDialog : DialogFragment() {

    var callback: OnAddFeedInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity is OnAddFeedInterface) {
            callback = activity as OnAddFeedInterface
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_feed, null)

        return AlertDialog.Builder(requireContext())
            .setTitle("Add Feed")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val url = view.findViewById<EditText>(R.id.editText).text.toString()
                callback?.onAddFeed(url)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
    }

    companion object {

        internal fun getInstance(): AddFeedDialog {
            return AddFeedDialog()
        }
    }
}