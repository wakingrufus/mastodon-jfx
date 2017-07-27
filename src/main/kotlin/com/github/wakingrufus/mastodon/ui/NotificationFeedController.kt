package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.feeds.NotificationController
import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import mu.KLogging
import java.io.IOException

class NotificationFeedController(private val statuses: ObservableList<Notification>) {
    companion object : KLogging()

    @FXML
    internal var notifications: VBox? = null

    fun initialize() {
        statuses.forEach {
            try {
                notifications?.children?.add(buildNotificationPanel(it))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        statuses.addListener { change: ListChangeListener.Change<out Notification>? ->
            while (change?.next()!!) {
                change.addedSubList?.forEach {
                    Platform.runLater({
                        notifications?.children?.add(element = buildNotificationPanel(it), index = 0)
                    })
                }
            }
        }
    }

    private fun buildNotificationPanel(notification: Notification): VBox {
        val tootController = NotificationController(notification)
        val fxmlLoader = FXMLLoader(javaClass.getResource("/notification.fxml"))
        fxmlLoader.setController(tootController)
        return fxmlLoader.load()
    }
}