package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.feeds.TootController
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import mu.KLogging
import java.io.IOException

class TootFeedController(private val statuses: ObservableList<Status>) {
    companion object : KLogging()

    @FXML
    internal var tootFeedWrapper: VBox? = null

    fun initialize() {
        statuses.forEach {
            val tootController = TootController(it)
            val fxmlLoader = FXMLLoader(javaClass.getResource("/toot.fxml"))
            fxmlLoader.setController(tootController)
            try {
                tootFeedWrapper?.children?.add(fxmlLoader.load())
            } catch (e: IOException) {
                logger.error("error loading feed view: " + e.localizedMessage, e)
            }
        }
    }
}