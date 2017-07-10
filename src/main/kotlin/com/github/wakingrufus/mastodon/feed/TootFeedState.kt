package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList


class TootFeedState(override val elements: List<FeedElement>) : FeedState<Status> {
    override val items: ObservableList<Status> = FXCollections.observableArrayList<Status>()

    override val feedType: FeedType = FeedType.TOOT
}