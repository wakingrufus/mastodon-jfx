package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.feed.FeedState
import com.github.wakingrufus.mastodon.ui.feeds.FeedCell
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.ListView
import mu.KLogging


class FeedsController(private val feedStates: ObservableList<FeedState<*>>) {
    companion object : KLogging()

    @FXML
    internal var feeds: ListView<FeedState<*>>? = null

    fun initialize() {
        this.feeds!!.items = feedStates
        feeds!!.setCellFactory { feeds -> FeedCell() }
    }
}