package com.inspiredandroid.newsout

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.content.Context
import android.os.Bundle

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
class NewsAccountAuthenticator(ctx: Context) : AbstractAccountAuthenticator(ctx) {

    override fun editProperties(accountAuthenticatorResponse: AccountAuthenticatorResponse, s: String): Bundle? {
        return null
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse,
        accountType: String,
        authTokenType: String,
        requiredFeatures: Array<String>,
        options: Bundle
    ): Bundle? {
        return null
    }

    override fun confirmCredentials(
        accountAuthenticatorResponse: AccountAuthenticatorResponse,
        account: Account,
        bundle: Bundle
    ): Bundle? {
        return null
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle? {
        return null
    }

    override fun getAuthTokenLabel(s: String): String? {
        return null
    }

    override fun updateCredentials(
        accountAuthenticatorResponse: AccountAuthenticatorResponse,
        account: Account,
        s: String,
        bundle: Bundle
    ): Bundle? {
        return null
    }

    override fun hasFeatures(
        accountAuthenticatorResponse: AccountAuthenticatorResponse,
        account: Account,
        strings: Array<String>
    ): Bundle? {
        return null
    }
}