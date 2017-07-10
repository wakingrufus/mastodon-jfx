package com.github.wakingrufus.mastodon.feed

import com.github.wakingrufus.mastodon.client.buildNotificationsClient
import com.github.wakingrufus.mastodon.client.buildPublicClient
import com.github.wakingrufus.mastodon.client.buildTimelinesClient
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.Range
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import com.sys1yagi.mastodon4j.api.method.Notifications
import com.sys1yagi.mastodon4j.api.method.Public
import com.sys1yagi.mastodon4j.api.method.Timelines
import lombok.extern.slf4j.Slf4j
import mu.KLogging

@Slf4j
class FeedWorker(private val feedState: FeedState<*>,
                 private val timelinesBuilder: (MastodonClient) -> Timelines = ::buildTimelinesClient,
                 private val publicBuilder: (MastodonClient) -> Public = ::buildPublicClient,
                 private val notificationsBuilder: (MastodonClient) -> Notifications = ::buildNotificationsClient)
    : Runnable {
    companion object : KLogging()

    override fun run() {
        logger.debug("starting feed worker for: " + feedState.feedType)
        for ((query, client) in feedState.elements) {
            if (query == FeedQuery.HOME) {
                logger.debug("fetching Home feed")
                val timelines = timelinesBuilder.invoke(client)
                try {
                    val statusPageable = timelines.getHome(Range()).execute()
                    val statuses = statusPageable.part
                    logger.info(statuses.size.toString() + " home statuses found")
                    for (s in statuses) {
                        (feedState as TootFeedState).addItem(s)
                    }
                } catch (e: Mastodon4jRequestException) {
                    logger.error("Error fetching feed: " + e.localizedMessage, e)
                }

            } else if (query == FeedQuery.PUBLIC) {
                logger.debug("fetching local public feed")
                val publicFeeds = publicBuilder.invoke(client)
                try {
                    val statusPageable = publicFeeds.getLocalPublic(Range()).execute()
                    val page = statusPageable.part
                    for (s in page) {
                        (feedState as TootFeedState).addItem(s)
                    }
                } catch (e: Mastodon4jRequestException) {
                    logger.error("Error fetching feed: " + e.localizedMessage, e)
                }

            } else if (query == FeedQuery.FEDERATED) {
                logger.debug("fetching federated feed")
                val publicFeeds = publicBuilder.invoke(client)
                try {
                    val statusIterator = publicFeeds.getFederatedPublic(Range()).execute().part.iterator()
                    while (statusIterator.hasNext()) {
                        val s = statusIterator.next()
                        (feedState as TootFeedState).addItem(s)
                    }
                } catch (e: Mastodon4jRequestException) {
                    logger.error("Error fetching feed: " + e.localizedMessage, e)
                }

            } else if (query == FeedQuery.NOTIFICATIONS) {
                val notifications = notificationsBuilder.invoke(client)
                logger.debug("fetching notifications feed")
                try {
                    val notificationList = notifications.getNotifications(Range()).execute().part
                    logger.info(notificationList.size.toString() + " notifications found")
                    for (notification in notificationList) {
                        (feedState as NotificationFeedState).addItem(notification)
                    }
                } catch (e: Mastodon4jRequestException) {
                    logger.error("Error fetching feed: " + e.localizedMessage, e)
                }

            } else {
                logger.warn("Invalid feed element query: " + query!!)
            }
        }
    }
}
