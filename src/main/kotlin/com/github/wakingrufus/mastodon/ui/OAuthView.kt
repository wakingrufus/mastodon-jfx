package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.client.completeOAuth
import com.github.wakingrufus.mastodon.client.createServerClient
import com.github.wakingrufus.mastodon.client.getOAuthUrl
import com.github.wakingrufus.mastodon.client.registerApp
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.OAuthModel
import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import mu.KLogging
import tornadofx.*

class OAuthView(val register: (MastodonClient) -> AppRegistration? = ::registerApp,
                val serverClient: (String) -> MastodonClient = ::createServerClient,
                val onComplete: (AccountState) -> Unit,
                val oAuthUrlBuilder: (OAuthModel) -> String = ::getOAuthUrl,
                val completeOAuthFunction: (OAuthModel) -> Unit = { model ->
                    completeOAuth(oAuth = model, onComplete = onComplete)
                })
    : View() {
    companion object : KLogging()

    lateinit var serverField: TextField
    lateinit var vbox: VBox
    lateinit var tokenField: TextField
    lateinit var loginWrapper: VBox
    override val root = vbox {
        id = "oauth-root"
        style {
            minWidth = 100.percent
            minHeight = 100.percent
            backgroundColor = multi(Color.rgb(0x06, 0x10, 0x18))
            padding = CssBox(top = 1.px, right = 1.px, bottom = 1.px, left = 1.px)
        }
        hbox {
            id = "instance-form"
            style {
                backgroundColor = multi(DefaultStyles.backgroundColor)
            }
            label("Server") {
                id = "instance-field-label"
                addClass(DefaultStyles.textInputLabel)
            }
            serverField = textfield {
                id = "instance-field"
                addClass(DefaultStyles.textInput)
            }
            button("Get Token") {
                id = "instance-form-submit"
                addClass(DefaultStyles.smallButton)
                action {
                    showLogin(serverField.text)
                }
            }
        }
        loginWrapper = vbox()
    }

    fun showLogin(serverUrl: String) {
        loginWrapper.clear()
        val client = serverClient(serverUrl)
        val oAuthModel = OAuthModel(appRegistration = register(client)!!, client = client)
        loginWrapper += webview {
            id = "instance-login"
            engine?.load(oAuthUrlBuilder.invoke(oAuthModel))
        }
        loginWrapper += hbox {
            id = "token-form"
            label("Token") {
                addClass(DefaultStyles.textInputLabel)
            }
            tokenField = textfield {
            }
            button("Login") {
                addClass(DefaultStyles.smallButton)
                action {
                    completeOAuthFunction(oAuthModel.copy(token = tokenField.text))
                }
            }
        }
    }
}