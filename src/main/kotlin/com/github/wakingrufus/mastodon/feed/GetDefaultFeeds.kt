package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}
fun getFeedsForAccount(accountClient: MastodonClient): ObservableList<FeedState<Status>> {
    logger.debug("getting feeds for client: " + accountClient.getInstanceName())
    val accountDefaultFeeds: ObservableList<FeedState<Status>> = FXCollections.observableArrayList()
    accountDefaultFeeds.add(TootFeedState(Collections.singletonList(FeedElement(FeedQuery.HOME, accountClient))))
    accountDefaultFeeds.add(TootFeedState(Collections.singletonList(FeedElement(FeedQuery.PUBLIC, accountClient))))
    accountDefaultFeeds.add(TootFeedState(Collections.singletonList(FeedElement(FeedQuery.FEDERATED, accountClient))))
    return accountDefaultFeeds
}