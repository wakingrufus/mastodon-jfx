package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.controllers.FeedsController
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import javafx.collections.ObservableList
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun viewAccountFeeds(parent: Pane,
                     feedStates: ObservableList<StatusFeed>,
                     accounts: List<AccountState>) {
    Viewer<ObservableList<StatusFeed>>(
            controller = { feeds ->
                FeedsController(
                        feedStates = feeds,
                        accountPrompter = { promptWithDialog(dialog = buildAccountDialog(accounts)) })
            },
            template = "/feeds.fxml")
            .view(parent = parent, item = feedStates, mode = ViewerMode.REPLACE)
}
