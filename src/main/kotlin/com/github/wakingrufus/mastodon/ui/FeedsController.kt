package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.feed.FeedState
import com.github.wakingrufus.mastodon.feed.FeedType
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.HBox
import mu.KLogging
import java.io.IOException


class FeedsController(private val feedStates: ObservableList<FeedState<Status>>) {
    companion object : KLogging()

    @FXML
    internal var feedsWrapper: HBox? = null

    fun initialize() {
        feedStates.forEach {
            if (it.feedType == FeedType.TOOT) {
                val tootController = TootFeedController(it.items)
                val fxmlLoader = FXMLLoader(javaClass.getResource("/toot-feed.fxml"))
                fxmlLoader.setController(tootController)
                try {
                    feedsWrapper?.children?.add(fxmlLoader.load())
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else {
                logger.warn("invalid feedtype: " + it.feedType.name)
            }
        }
    }
}