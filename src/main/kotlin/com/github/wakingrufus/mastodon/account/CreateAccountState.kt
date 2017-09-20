package com.github.wakingrufus.mastodon.account

import com.github.wakingrufus.mastodon.client.buildNotificationsClient
import com.github.wakingrufus.mastodon.client.buildPublicClient
import com.github.wakingrufus.mastodon.client.buildStreamingClient
import com.github.wakingrufus.mastodon.client.buildTimelinesClient
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.feed.populateNotificationFeed
import com.github.wakingrufus.mastodon.feed.populateTootFeed
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import com.sys1yagi.mastodon4j.api.method.Accounts
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun createAccountState(client: MastodonClient): AccountState {
    val accountsClient = Accounts(client)
    try {
        val account = accountsClient.getVerifyCredentials().execute()
        val streamingClient = buildStreamingClient(client)
        val newAccountState = AccountState(
                account = account,
                client = client)
        launch(CommonPool) {
            val homeShutdownable = populateTootFeed(
                    feed = newAccountState.homeFeed.statuses,
                    fetcher = buildTimelinesClient(client)::getHome,
                    listener = streamingClient::user)
            val publicShutdownable = populateTootFeed(
                    feed = newAccountState.publicFeed.statuses,
                    fetcher = buildPublicClient(client)::getLocalPublic,
                    listener = streamingClient::localPublic)
            val federatedShutdownable = populateTootFeed(
                    feed = newAccountState.federatedFeed.statuses,
                    fetcher = buildPublicClient(client)::getFederatedPublic,
                    listener = streamingClient::federatedPublic)
            val notificationShutdownable = populateNotificationFeed(
                    feed = newAccountState.notificationFeed.notifications,
                    fetcher = buildNotificationsClient(client)::getNotifications,
                    listener = streamingClient::user)
        }
        return newAccountState
    } catch (e: Mastodon4jRequestException) {
        logger.error("error fetching account: " + e.message, e)
        throw Exception("error fetching account: " + e.message)
    }
}
