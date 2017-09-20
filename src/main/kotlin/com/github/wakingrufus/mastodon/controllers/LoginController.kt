package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.client.createServerClient
import com.github.wakingrufus.mastodon.client.registerApp
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import mu.KLogging

class LoginController(private val startOAuth: (AppRegistration, MastodonClient) -> Any) : Controller<Unit> {
    companion object : KLogging()

    @FXML
    internal var serverField: TextField? = null

    @FXML
    internal var tokenButton: Button? = null

    @FXML
    override fun initialize() {
        tokenButton?.setOnAction { e ->
            logger.info("server: ${serverField?.text}")
            val client = createServerClient(serverField!!.text)
            startOAuth(registerApp(client)!!, client)
        }
    }
}