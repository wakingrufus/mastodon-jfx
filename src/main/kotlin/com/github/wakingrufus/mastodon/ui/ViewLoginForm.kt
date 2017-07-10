package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.login.LoginController
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import mu.KotlinLogging
import java.io.IOException

private val logger = KotlinLogging.logger {}
fun viewLoginForm(parent: Pane) {
    logger.info("Preparing to create new Id")
    try {
        val fxmlLoader = FXMLLoader(object : Any() {}.javaClass.getResource("/login.fxml"))
        val loginController = LoginController()
        fxmlLoader.setController(loginController)
        parent.children.clear()
        parent.children.add(fxmlLoader.load())
    } catch (ex: IOException) {
        logger.error("error loading login pane: " + ex.message, ex)
    }

}
