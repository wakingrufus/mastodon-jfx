package com.github.wakingrufus.mastodon.feed

import javafx.collections.ObservableList

fun fetchFeed(feedState: FeedState<*>): ObservableList<*> {
    val feedWorker = FeedWorker(feedState)
    feedWorker.run()
    return feedState.items
}