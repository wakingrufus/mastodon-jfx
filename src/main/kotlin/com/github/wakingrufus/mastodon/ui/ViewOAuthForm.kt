package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.login.OauthController
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import mu.KotlinLogging
import java.io.IOException

private val logger = KotlinLogging.logger {}
fun viewOAuthForm(parent: Pane, client: MastodonClient, appRegistration: AppRegistration, oauthUrl: String) {
    logger.info("Preparing to create new Id")
    try {
        val fxmlLoader = FXMLLoader(object : Any() {}.javaClass.getResource("/oauth.fxml"))
        val loginController = OauthController(client, appRegistration, oauthUrl)
        fxmlLoader.setController(loginController)
        parent.children.clear()
        parent.children.add(fxmlLoader.load())
    } catch (ex: IOException) {
        logger.error("error loading login pane: " + ex.localizedMessage, ex)
    }

}
