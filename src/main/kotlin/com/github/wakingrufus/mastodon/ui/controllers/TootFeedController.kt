package com.github.wakingrufus.mastodon.ui.controllers

import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.layout.VBox
import mu.KLogging
import java.io.IOException

class TootFeedController(private val statuses: ObservableList<Status>,
                         private val accountPrompter: () -> AccountState?,
                         private val statusViewer: Viewer<Status> = Viewer(
                                 controller = { item ->
                                     TootController(
                                             accountPrompter = accountPrompter,
                                             status = item)
                                 },
                                 template = "/toot.fxml")) {
    companion object : KLogging()

    @FXML
    internal var tootFeedWrapper: VBox? = null

    fun initialize() {
        statuses.forEach {
            try {
                statusViewer.view(parent = tootFeedWrapper!!, item = it)
            } catch (e: IOException) {
                logger.error("error loading feed view: " + e.localizedMessage, e)
            }
        }

        statuses.addListener { change: ListChangeListener.Change<out Status>? ->
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