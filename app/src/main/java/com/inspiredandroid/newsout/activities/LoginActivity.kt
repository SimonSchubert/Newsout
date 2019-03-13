package com.inspiredandroid.newsout.activities

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.inspiredandroid.newsout.Api
import com.inspiredandroid.newsout.R
import com.inspiredandroid.newsout.dialogs.InfoDialog
import io.ktor.util.InternalAPI
import kotlinx.android.synthetic.main.activity_login.*

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
@InternalAPI
class LoginActivity : AppCompatActivity() {

    var mode = MODE_NEWSOUT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        updateButtons()

        passwordEt.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                if (mode == MODE_NEWSOUT) {
                    attemptSignup()
                } else {
                    attemptLogin()
                }
                return@OnEditorActionListener true
            }
            false
        })

        signInBtn.setOnClickListener {
            if (mode == MODE_NEWSOUT) {
                attemptSignup()
            } else {
                attemptLogin()
            }
        }
        infoBtn.setOnClickListener {
            val dialog = InfoDialog.getInstance()
            dialog.show(supportFragmentManager, "TAG")
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            mode = if (checkedId == R.id.nextcloudBtn) {
                MODE_CUSTOM
            } else {
                MODE_NEWSOUT
            }
            updateButtons()
        }

        val accountManager = AccountManager.get(this)
        accountManager.getAccountsByType("com.inspiredandroid.newsout").forEach {
            val password = accountManager.getPassword(it)
            val url = accountManager.getUserData(it, "EXTRA_BASE_URL")
            Api.setCredentials(url, it.name, password)

            startActivity(Intent(this@LoginActivity, FeedsActivity::class.java))
            finish()
        }
    }

    private fun updateButtons() {
        if (mode == MODE_NEWSOUT) {
            serverPathEt.visibility = View.GONE
            signInBtn.text = "Login/Create"
            emailEt.hint = "Email"
        } else {
            serverPathEt.visibility = View.VISIBLE
            signInBtn.text = "Login"
            emailEt.hint = "Username/Email"
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

        Api.login(nextcloudUrl, email, password, {
            saveLogin(nextcloudUrl, email, password)

            startActivity(Intent(this@LoginActivity, FeedsActivity::class.java))
            finish()
        }, {
            passwordEt.error = "Password or email might be wrong"
            hideLoading()
        }, {
            serverPathEt.error = "Could not connect"
            hideLoading()
        })
    }

    fun attemptSignup() {
        emailEt.error = null
        passwordEt.error = null
        serverPathEt.error = null

        progressBar.visibility = View.VISIBLE
        ivLogo.visibility = View.GONE

        val email = emailEt.text.toString()
        val password = passwordEt.text.toString()
        val nextcloudUrl = "https://arnald.ocloud.de"

        Api.createAccount(nextcloudUrl, email, password, {
            saveLogin(nextcloudUrl, email, password)

            startActivity(Intent(this@LoginActivity, FeedsActivity::class.java))
            finish()
        }, {
            serverPathEt?.setText(nextcloudUrl)
            attemptLogin()
        }, {
            passwordEt.error = "Try again with a different password"
            hideLoading()
        })
    }

    fun saveLogin(email: String, password: String, url: String) {
        val accountManager = AccountManager.get(this)
        val account = Account(email, "com.inspiredandroid.newsout")
        accountManager.addAccountExplicitly(account, password, Bundle())
        accountManager.setUserData(account, "EXTRA_BASE_URL", url)
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
        ivLogo.visibility = View.VISIBLE
    }

    companion object {
        const val MODE_CUSTOM = 1
        const val MODE_NEWSOUT = 2
    }
}
