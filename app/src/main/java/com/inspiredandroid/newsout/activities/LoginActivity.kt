package com.inspiredandroid.newsout.activities

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.inspiredandroid.newsout.ApplicationApi
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.dialogs.InfoDialog
import io.ktor.util.InternalAPI
import kotlinx.android.synthetic.main.activity_login.*


/**
 * A login screen that offers login via email/password.
 */
@InternalAPI
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        passwordEt.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        signInBtn.setOnClickListener { attemptLogin() }
        infoBtn.setOnClickListener {
            val dialog = InfoDialog.getInstance()
            dialog.show(supportFragmentManager, "TAG")
        }

        val accountManager = AccountManager.get(this)
        accountManager.getAccountsByType("com.inspiredandroid.newsout").forEach {
            val password = accountManager.getPassword(it)
            val url = accountManager.getUserData(it, "EXTRA_BASE_URL")
            ApplicationApi.setCredentials(url, it.name, password)

            startActivity(Intent(this@LoginActivity, FeedsActivity::class.java))
            finish()
        }
    }

    private fun attemptLogin() {
        emailEt.error = null
        passwordEt.error = null
        serverPathEt.error = null

        progressBar.visibility = View.VISIBLE
        ivLogo.visibility = View.GONE

        val email = emailEt.text.toString()
        val password = passwordEt.text.toString()
        val nextcloudUrl = serverPathEt.text.toString()

        ApplicationApi.login(nextcloudUrl, email, password, {
            val accountManager = AccountManager.get(this)
            val account = Account(email, "com.inspiredandroid.newsout")
            accountManager.addAccountExplicitly(account, password, Bundle())
            accountManager.setUserData(account, "EXTRA_BASE_URL", nextcloudUrl)

            startActivity(Intent(this@LoginActivity, FeedsActivity::class.java))
            finish()
        }, {
            passwordEt.error = "Password or email might be wrong"
            progressBar.visibility = View.GONE
            ivLogo.visibility = View.VISIBLE
        },
            {
                serverPathEt.error = "Could not connect"
                progressBar.visibility = View.GONE
                ivLogo.visibility = View.VISIBLE
            })
    }
}
