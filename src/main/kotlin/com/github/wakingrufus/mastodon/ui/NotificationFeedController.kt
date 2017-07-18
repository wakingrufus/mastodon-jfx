package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.feeds.NotificationController
import com.github.wakingrufus.mastodon.ui.feeds.TootController
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.HBox
import mu.KLogging
import java.io.IOException

class NotificationFeedController(private val statuses: ObservableList<Notification>) {
    companion object : KLogging()

    @FXML
    internal var feedsWrapper: HBox? = null

    fun initialize() {
        statuses.forEach {
            val tootController = NotificationController(it)
            val fxmlLoader = FXMLLoader(javaClass.getResource("/notification.fxml"))
            fxmlLoader.setController(tootController)
            try {
                feedsWrapper?.children?.add(fxmlLoader.load())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}