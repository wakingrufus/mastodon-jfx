package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Notification
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}
fun getNotificationFeedForAccount(accountClient: MastodonClient): FeedState<Notification> {
    logger.debug("getting feeds for client: " + accountClient.getInstanceName())
    return NotificationFeedState(Collections.singletonList(FeedElement(FeedQuery.NOTIFICATIONS, accountClient)))
}