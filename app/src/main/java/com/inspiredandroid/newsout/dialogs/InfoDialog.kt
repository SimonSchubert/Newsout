package com.inspiredandroid.newsout.dialogs

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.inspiredandroid.newsout.R

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class InfoDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_info, null)

        view.findViewById<View>(R.id.btnRate).setOnClickListener {
            open("https://play.google.com/store/apps/details?id=com.inspiredandroid.newsout")
        }
        view.findViewById<View>(R.id.btnGithub).setOnClickListener {
            open("https://github.com/simonschubert/newsout")
        }
        view.findViewById<View>(R.id.btnCompare).setOnClickListener {
            open("https://nextcloud.com/compare/")
        }

        return AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Info")
            .setView(view)
            .create()
    }

    private fun open(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    companion object {

        internal fun getInstance(): InfoDialog {
            return InfoDialog()
        }
    }
}