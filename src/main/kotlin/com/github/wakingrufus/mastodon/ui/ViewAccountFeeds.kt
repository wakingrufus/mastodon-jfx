package com.github.wakingrufus.mastodon.ui

import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun viewAccountFeeds(parent: Pane, feedStates: ObservableList<ObservableList<Status>>) {
    Viewer<ObservableList<ObservableList<Status>>>(controller = { feeds -> FeedsController(feeds) },
            template = "/feeds.fxml")
            .view(parent = parent, item = feedStates, mode = ViewerMode.REPLACE)
}
