package com.github.wakingrufus.mastodon.account

import com.github.wakingrufus.mastodon.client.buildPublicClient
import com.github.wakingrufus.mastodon.client.buildStreamingClient
import com.github.wakingrufus.mastodon.client.buildTimelinesClient
import com.github.wakingrufus.mastodon.feed.populateTootFeed
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import com.sys1yagi.mastodon4j.api.method.Accounts
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun createAccountState(client: MastodonClient): AccountState {
    val accountsClient = Accounts(client)
    try {
        val account = accountsClient.getVerifyCredentials().execute()
        val newAccountState = AccountState(
                account = account,
                client = client)
        val homeShutdownable = populateTootFeed(
                feed = newAccountState.homeFeed,
                fetcher = buildTimelinesClient(client)::getHome,
                listener = buildStreamingClient(client)::user)
        val publicShutdownable = populateTootFeed(
                feed = newAccountState.publicFeed,
                fetcher = buildPublicClient(client)::getLocalPublic,
                listener = buildStreamingClient(client)::localPublic)
        val federatedShutdownable = populateTootFeed(
                feed = newAccountState.federatedFeed,
                fetcher = buildPublicClient(client)::getFederatedPublic,
                listener = buildStreamingClient(client)::federatedPublic)
        return newAccountState
    } catch (e: Mastodon4jRequestException) {
        logger.error("error fetching account: " + e.message, e)
        throw Exception("error fetching account: " + e.message)
    }
}
