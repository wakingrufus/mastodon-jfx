package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.events.ServerConnectEvent
import com.github.wakingrufus.mastodon.ui.Controller
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import mu.KLogging

class LoginController : Controller<Unit> {
    companion object : KLogging()

    @FXML
    internal var serverField: TextField? = null

    @FXML
    internal var tokenButton: Button? = null

    @FXML
    override fun initialize() {
        tokenButton?.setOnAction { e ->
            logger.info("server: ${serverField?.text}")
            Event.fireEvent(e.target, ServerConnectEvent(e.source, e.target, serverField?.text))
        }
    }
}