package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.feeds.TootController
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.VBox
import mu.KLogging
import java.io.IOException

class TootFeedController(private val statuses: ObservableList<Status>) {
    companion object : KLogging()

    @FXML
    internal var tootFeedWrapper: VBox? = null

    fun initialize() {
        statuses.forEach {
            try {
                tootFeedWrapper?.children?.add(buildTootPanel(it))
            } catch (e: IOException) {
                logger.error("error loading feed view: " + e.localizedMessage, e)
            }
        }

        statuses.addListener { change: ListChangeListener.Change<out Status>? ->
            while (change?.next()!!) {
                change.addedSubList?.forEach {
                    Platform.runLater({
                        tootFeedWrapper?.children?.add(element = buildTootPanel(it), index = 0)
                    })
                }
            }
        }
    }

    private fun buildTootPanel(toot: Status): VBox {
        val tootController = TootController(toot)
        val fxmlLoader = FXMLLoader(javaClass.getResource("/toot.fxml"))
        fxmlLoader.setController(tootController)
        return fxmlLoader.load()
    }
}