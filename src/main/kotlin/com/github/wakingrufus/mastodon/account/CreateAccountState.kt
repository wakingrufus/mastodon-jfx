package com.github.wakingrufus.mastodon.account

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import com.sys1yagi.mastodon4j.api.method.Accounts
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun createAccountState(client: MastodonClient): AccountState {
    val accountsClient = Accounts(client)
    try {
        val account = accountsClient.getVerifyCredentials().execute()
        val newAccountState = AccountState(account, client)
        return newAccountState
    } catch (e: Mastodon4jRequestException) {
        logger.error("error fetching account: " + e.message, e)
        throw Exception("error fetching account: " + e.message)
    }
}
