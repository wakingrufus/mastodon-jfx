package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import mu.KLogging
import java.io.IOException

class TootFeedController(private val statusFeed: StatusFeed,
                         private val accountPrompter: () -> AccountState?,
                         private val statusViewer: Viewer<Status> = Viewer(
                                 controller = { item ->
                                     TootController(
                                             accountPrompter = accountPrompter,
                                             status = item)
                                 },
                                 template = "/toot.fxml")) : Controller<StatusFeed> {
    companion object : KLogging()

    @FXML
    internal var tootFeedWrapper: VBox? = null

    @FXML
    internal var feedLabel: Label? = null

    override fun initialize() {
        feedLabel?.text = statusFeed.name+ " @ "+ statusFeed.server
        statusFeed.statuses.forEach {
            try {
                statusViewer.view(parent = tootFeedWrapper!!, item = it)
            } catch (e: IOException) {
                logger.error("error loading feed view: " + e.localizedMessage, e)
            }
        }

        statusFeed.statuses.addListener { change: ListChangeListener.Change<out Status>? ->
            while (change?.next()!!) {
                change.addedSubList?.forEach {
                    Platform.runLater({
                        statusViewer.view(parent = tootFeedWrapper!!, item = it, mode = ViewerMode.PREPEND)
                    })
                }
            }
        }
    }
}