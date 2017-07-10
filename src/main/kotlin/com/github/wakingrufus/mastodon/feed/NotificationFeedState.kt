package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class NotificationFeedState(override val elements: List<FeedElement>) : FeedState<Notification> {
    override val items: ObservableList<Notification> = FXCollections.observableArrayList<Notification>()

    override val feedType: FeedType = FeedType.TOOT
}