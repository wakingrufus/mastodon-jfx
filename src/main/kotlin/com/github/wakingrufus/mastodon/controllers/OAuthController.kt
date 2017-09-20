package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.OAuthModel
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.web.WebView
import mu.KLogging

class OAuthController(private val oAuthModel: OAuthModel,
                      private val oAuthurl: String,
                      private val completeOAuth: (OAuthModel) -> Any)
    : Controller<OAuthModel> {
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
            completeOAuth.invoke(oAuthModel.copy(token = tokenField!!.text))
        }
    }
}
