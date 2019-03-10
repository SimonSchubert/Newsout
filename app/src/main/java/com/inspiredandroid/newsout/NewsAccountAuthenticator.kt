package com.inspiredandroid.newsout

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.content.Context
import android.os.Bundle

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