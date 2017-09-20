package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.MastodonApplication
import javafx.fxml.FXMLLoader
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import mu.KLogging
import java.io.IOException


class MainView(val rootEm: Double) {
    companion object : KLogging()

    val root: BorderPane

    init {
        val fxmlLoader = FXMLLoader(MastodonApplication::class.java.getResource("/main.fxml"))

        try {
            root = fxmlLoader.load<BorderPane>()
        } catch (e: IOException) {
            logger.error("error loading main: ${e.localizedMessage}", e)
            throw RuntimeException("error loading main: ${e.localizedMessage}")
        }

        //    root.setStyle("-fx-min-height: 100%;");

    }

    fun newMainView(): Pane {
        val pane = mainPane()
        pane.setMinHeight(rootEm * 60)
        root.center = pane
        return pane
    }

    fun newNotificationView(): Pane {
        val pane = mainPane()
        pane.minWidth = rootEm * 10
        root.right = pane
        return pane
    }

    fun newSettingsView(): Pane {
        val pane = mainPane()
        root.left = pane
        return pane
    }
}