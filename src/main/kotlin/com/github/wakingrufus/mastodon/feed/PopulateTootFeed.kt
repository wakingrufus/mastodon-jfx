package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.MastodonRequest
import com.sys1yagi.mastodon4j.api.Handler
import com.sys1yagi.mastodon4j.api.Pageable
import com.sys1yagi.mastodon4j.api.Range
import com.sys1yagi.mastodon4j.api.Shutdownable
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import javafx.collections.ObservableList
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun populateTootFeed(feed: ObservableList<Status>,
                     fetcher: (Range) -> MastodonRequest<Pageable<Status>>,
                     listener: (Handler) -> Shutdownable): Shutdownable {
    logger.debug("fetching Home feed")
    try {
        val statusPageable = fetcher.invoke(Range()).execute()
        val statuses = statusPageable.part
        feed += statuses

    } catch (e: Mastodon4jRequestException) {
        logger.error("Error fetching feed: " + e.localizedMessage, e)
    }

    val shutdownable = listener.invoke(object : Handler {
        override fun onStatus(status: Status) {
            feed.add(element = status, index = 0)
        }

        override fun onNotification(notification: Notification) {
        }

        override fun onDelete(id: Long) {/* no op */
        }
    })
    return shutdownable
}