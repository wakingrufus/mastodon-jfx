package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.controllers.NotificationFeedController
import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.collections.ObservableList
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun viewAccountNotifications(parent: Pane,
                             notifications: ObservableList<Notification>,
                             accounts: List<AccountState>) {
    Viewer<ObservableList<Notification>>(controller = { n ->
        NotificationFeedController(statuses = n,
                accountPrompter = { promptWithDialog(dialog = buildAccountDialog(accounts)) })
    },
            template = "/notification-feed.fxml")
            .view(parent = parent, item = notifications, mode = ViewerMode.REPLACE)
}
