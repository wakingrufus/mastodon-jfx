package com.github.wakingrufus.mastodon.feed

import javafx.collections.ObservableList

interface FeedState<T> {
    val feedType: FeedType

    fun addItem(item: T) {
        items.add(item)
    }

    val items: ObservableList<T>

    val elements: List<FeedElement>
}
