package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.controllers.NotificationFeedController
import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.collections.ObservableList
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun viewAccountNotifications(parent: Pane, notifications: ObservableList<Notification>) {
    Viewer<ObservableList<Notification>>(controller = { n -> NotificationFeedController(n) },
            template = "/notification-feed.fxml")
            .view(parent = parent, item = notifications, mode = ViewerMode.REPLACE)
}
