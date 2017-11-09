package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.MastodonRequest
import com.sys1yagi.mastodon4j.api.Handler
import com.sys1yagi.mastodon4j.api.Pageable
import com.sys1yagi.mastodon4j.api.Range
import com.sys1yagi.mastodon4j.api.Shutdownable
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import javafx.application.Platform
import javafx.collections.ObservableList
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun populateNotificationFeed(feed: ObservableList<Notification>,
                             fetcher: (Range) -> MastodonRequest<Pageable<Notification>>,
                             listener: (Handler) -> Shutdownable): Shutdownable {
    try {
        val statusPageable = fetcher.invoke(Range()).execute()
        val statuses = statusPageable.part
        logger.info(statuses.size.toString() + " notifications found")
        Platform.runLater({
            feed += statuses
        })

    } catch (e: Mastodon4jRequestException) {
        logger.error("Error fetching feed: " + e.localizedMessage, e)
    }

    val shutdownable = listener.invoke(object : Handler {
        override fun onStatus(status: Status) {
        }

        override fun onNotification(notification: Notification) {
            Platform.runLater({
                logger.info { "receiving notification: ${notification.type}" }
                feed.add(element = notification, index = 0)
            })
        }

        override fun onDelete(id: Long) {/* no op */
        }
    })
    return shutdownable
}