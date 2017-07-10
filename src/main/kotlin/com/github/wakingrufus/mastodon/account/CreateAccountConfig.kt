package com.github.wakingrufus.mastodon.account

import com.sys1yagi.mastodon4j.api.method.Accounts

fun createAccountConfig(accountClient: Accounts, accessToken: String, clientId: String, clientSecret: String, server: String): AccountConfig {
    val account = accountClient.getVerifyCredentials().execute()
    val accountConfig = AccountConfig(
            accessToken = accessToken,
            clientId = clientId,
            clientSecret = clientSecret,
            username = account.userName,
            server = server)
    return accountConfig
}