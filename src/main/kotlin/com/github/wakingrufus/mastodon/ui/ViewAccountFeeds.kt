package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.feed.FeedState
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import mu.KotlinLogging
import java.io.IOException

private val logger = KotlinLogging.logger {}
fun viewAccountFeeds(parent: Pane, feedStates: ObservableList<FeedState<Status>>) {
    parent.children.clear()
    val feedsController = FeedsController(feedStates)
    val fxmlLoader = FXMLLoader(object : Any() {}.javaClass.getResource("/feeds.fxml"))
    fxmlLoader.setController(feedsController)
    try {
        parent.children.add(fxmlLoader.load())
    } catch (e: IOException) {
        logger.error("error loading feeds pane: " + e.message, e)
    }

}
