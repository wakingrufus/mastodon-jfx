package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.layout.HBox
import mu.KLogging

class FeedsController(private val feedStates: ObservableList<StatusFeed>,
                      private val accountPrompter: () -> AccountState?,
                      private val feedViewer: Viewer<StatusFeed> = Viewer(
                              controller = { list ->
                                  TootFeedController(
                                          statusFeed = list,
                                          accountPrompter = accountPrompter)
                              },
                              template = "/toot-feed.fxml")) : Controller<ObservableList<StatusFeed>> {
    companion object : KLogging()

    @FXML
    internal var feedsWrapper: HBox? = null

    @FXML
    override fun initialize() {
        feedStates.forEach {
            feedViewer.view(parent = feedsWrapper!!, item = it, mode = ViewerMode.APPEND)
        }

        feedStates.addListener { change: ListChangeListener.Change<out StatusFeed>? ->
            while (change?.next()!!) {
                change.addedSubList?.forEach {
                    feedViewer.view(parent = feedsWrapper!!, item = it, mode = ViewerMode.PREPEND)
                }
            }
        }
    }
}