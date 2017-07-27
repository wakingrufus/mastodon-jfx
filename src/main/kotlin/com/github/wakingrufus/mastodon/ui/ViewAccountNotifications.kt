package com.github.wakingrufus.mastodon.ui

import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.collections.ObservableList
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import mu.KotlinLogging
import java.io.IOException

private val logger = KotlinLogging.logger {}
fun viewAccountNotifications(parent: Pane, notifications: ObservableList<Notification>) {
    parent.children.clear()
    val controller = NotificationFeedController(notifications)
    val fxmlLoader = FXMLLoader(object : Any() {}.javaClass.getResource("/notification-feed.fxml"))
    fxmlLoader.setController(controller)
    try {
        parent.children.add(fxmlLoader.load())
    } catch (e: IOException) {
        logger.error("error loading notifications pane: " + e.message, e)
    }
}
