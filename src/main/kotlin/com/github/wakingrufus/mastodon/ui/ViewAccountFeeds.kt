package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.account.AccountState
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun viewAccountFeeds(parent: Pane,
                     feedStates: ObservableList<ObservableList<Status>>,
                     accounts: List<AccountState>) {
    Viewer<ObservableList<ObservableList<Status>>>(
            controller = { feeds ->
                FeedsController(
                        feedStates = feeds,
                        accountPrompter = { promptWithDialog(dialog = buildAccountDialog(accounts)) })
            },
            template = "/feeds.fxml")
            .view(parent = parent, item = feedStates, mode = ViewerMode.REPLACE)
}
