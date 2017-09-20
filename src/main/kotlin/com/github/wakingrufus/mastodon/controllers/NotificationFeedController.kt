package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.NotificationFeed
import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.VBox
import mu.KLogging
import java.io.IOException

class NotificationFeedController(private val notificationFeed: NotificationFeed,
                                 private val accountPrompter: () -> AccountState?)
    : Controller<NotificationFeed> {
    companion object : KLogging()

    @FXML
    internal var notifications: VBox? = null

    @FXML
    override fun initialize() {
        notificationFeed.notifications.forEach {
            try {
                notifications?.children?.add(buildNotificationPanel(it))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        notificationFeed.notifications.addListener { change: ListChangeListener.Change<out Notification>? ->
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
        val tootController = NotificationController(
                notification = notification,
                accountPrompter = accountPrompter
        )
        val fxmlLoader = FXMLLoader(javaClass.getResource("/notification.fxml"))
        fxmlLoader.setController(tootController)
        return fxmlLoader.load()
    }
}