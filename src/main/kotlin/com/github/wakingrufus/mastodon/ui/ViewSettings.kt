package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.account.AccountState
import javafx.collections.ObservableList
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import mu.KotlinLogging
import java.io.IOException

private val logger = KotlinLogging.logger {}
fun viewSettings(parent: Pane, accountList: ObservableList<AccountState>) {
    parent.children.clear()
    val settingsController = SettingsController(accountList)
    try {
        val fxmlLoader = FXMLLoader(object : Any() {}.javaClass.getResource("/settings.fxml"))
        fxmlLoader.setController(settingsController)
        parent.children.add(fxmlLoader.load())
    } catch (e: IOException) {
        logger.error("error loading settings pane: " + e.localizedMessage, e)
    }
}
