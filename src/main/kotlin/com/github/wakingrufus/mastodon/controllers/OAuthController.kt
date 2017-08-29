package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.events.OAuthStartEvent
import com.github.wakingrufus.mastodon.events.OAuthTokenEvent
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.web.WebView
import mu.KLogging

class OAuthController(private val client: MastodonClient,
                      private val appRegistration: AppRegistration,
                      private val oAuthurl: String) : Controller<OAuthStartEvent> {
    companion object : KLogging()

    @FXML
    internal var tokenField: TextField? = null
    @FXML
    internal var loginButton: Button? = null
    @FXML
    internal var webView: WebView? = null

    @FXML
    override fun initialize() {
        webView?.engine?.load(oAuthurl)
        loginButton?.setOnAction { event ->
            val OAuthTokenEvent = OAuthTokenEvent(
                    source = event.source,
                    target = event.target,
                    appRegistration = appRegistration,
                    token = tokenField!!.text,
                    client = client)
            Event.fireEvent(event.target, OAuthTokenEvent)
        }
    }
}
