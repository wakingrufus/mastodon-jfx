package com.github.wakingrufus.mastodon.account

import com.github.wakingrufus.mastodon.client.buildNotificationsClient
import com.github.wakingrufus.mastodon.client.buildPublicClient
import com.github.wakingrufus.mastodon.client.buildStreamingClient
import com.github.wakingrufus.mastodon.client.buildTimelinesClient
import com.github.wakingrufus.mastodon.data.AccountState
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.MastodonRequest
import com.sys1yagi.mastodon4j.api.Handler
import com.sys1yagi.mastodon4j.api.Pageable
import com.sys1yagi.mastodon4j.api.Range
import com.sys1yagi.mastodon4j.api.Shutdownable
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import com.sys1yagi.mastodon4j.api.method.Accounts
import javafx.application.Platform
import javafx.collections.ObservableList
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun createAccountState(client: MastodonClient,
                       onNotification: (Notification) -> Unit)
        : AccountState {
    val accountsClient = Accounts(client)
    try {
        val account = accountsClient.getVerifyCredentials().execute()
        val streamingClient = buildStreamingClient(client)
        val newAccountState = AccountState(
                account = account,
                client = client)
        launch(CommonPool) {
            logger.debug("fetching Home feed")

            fetchAndAddToFeed(newAccountState.homeFeed.statuses, buildTimelinesClient(client)::getHome)

            val notifications = fetchAndAddToFeed(
                    newAccountState.notificationFeed.notifications,
                    buildNotificationsClient(client)::getNotifications)

            logger.info(notifications.size.toString() + " notifications found")

            val shutdownable = monitorUserFeeds(
                    homeFeed = newAccountState.homeFeed.statuses,
                    notificationFeed = newAccountState.notificationFeed.notifications,
                    client = client,
                    onNotification = onNotification)

            fetchAndAddToFeed(newAccountState.publicFeed.statuses, buildPublicClient(client)::getLocalPublic)
            monitorPublicFeed(newAccountState.publicFeed.statuses, streamingClient::localPublic)

            fetchAndAddToFeed(newAccountState.federatedFeed.statuses, buildPublicClient(client)::getFederatedPublic)
            monitorPublicFeed(newAccountState.federatedFeed.statuses, streamingClient::federatedPublic)

        }
        return newAccountState
    } catch (e: Mastodon4jRequestException) {
        logger.error("error fetching account: " + e.message, e)
        throw Exception("error fetching account: " + e.message)
    }
}

/**
 * @return the new items
 */
fun <T> fetchAndAddToFeed(feed: ObservableList<T>,
                          fetcher: (Range) -> MastodonRequest<Pageable<T>>): List<T> {
    return try {
        val statusPageable = fetcher.invoke(Range()).execute()
        val statuses = statusPageable.part
        Platform.runLater({
            feed += statuses
        })
        statuses
    } catch (e: Mastodon4jRequestException) {
        logger.error("Error fetching feed: " + e.localizedMessage, e)
        emptyList()
    }
}

fun monitorPublicFeed(feed: ObservableList<Status>,
                      listener: (Handler) -> Shutdownable)
        : Shutdownable {

    return listener(object : Handler {
        override fun onStatus(status: Status) {
            Platform.runLater({
             //   logger.info { "receiving status: ${status.content}" }
                feed.add(element = status, index = 0)
            })
        }

        override fun onNotification(notification: Notification) {
        }

        override fun onDelete(id: Long) {/* no op */
        }
    })
}

fun monitorUserFeeds(homeFeed: ObservableList<Status>,
                     notificationFeed: ObservableList<Notification>,
                     client: MastodonClient,
                     onNotification: (Notification) -> Unit)
        : Shutdownable {

    return buildStreamingClient(client).user(object : Handler {
        override fun onStatus(status: Status) {
            Platform.runLater({
                homeFeed.add(element = status, index = 0)
            })
        }

        override fun onNotification(notification: Notification) {
            Platform.runLater({
                logger.info { "receiving notification: ${notification.type}" }
                notificationFeed.add(element = notification, index = 0)
                onNotification(notification)
            })
        }

        override fun onDelete(id: Long) {/* no op */
        }
    })
}
