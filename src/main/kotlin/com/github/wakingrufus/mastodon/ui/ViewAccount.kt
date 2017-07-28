package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.controllers.AccountController
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import mu.KotlinLogging
import java.io.IOException

private val logger = KotlinLogging.logger {}
fun viewAccount(parent: Pane, account: Account) {
    parent.children.clear()
    val controller = AccountController(account)
    try {
        val fxmlLoader = FXMLLoader(object : Any() {}.javaClass.getResource("/account.fxml"))
        fxmlLoader.setController(controller)
        parent.children.add(fxmlLoader.load())
    } catch (e: IOException) {
        logger.error("error loading account pane: " + e.localizedMessage, e)
    }
}
