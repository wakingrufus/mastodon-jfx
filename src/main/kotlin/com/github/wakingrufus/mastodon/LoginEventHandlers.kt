package com.github.wakingrufus.mastodon

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.account.addAccountToConfig
import com.github.wakingrufus.mastodon.account.createAccountConfig
import com.github.wakingrufus.mastodon.account.createAccountState
import com.github.wakingrufus.mastodon.client.*
import com.github.wakingrufus.mastodon.config.Config
import com.github.wakingrufus.mastodon.controllers.OAuthController
import com.github.wakingrufus.mastodon.events.*
import com.github.wakingrufus.mastodon.ui.*
import com.sys1yagi.mastodon4j.api.method.Accounts
import javafx.collections.ObservableList
import javafx.event.Event
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun attachLoginEventHandlersFromJava(addToView: (Pane) -> Pane,
                                     rootPane: BorderPane,
                                     accountList: ObservableList<AccountState>,
                                     config: Config) {
    attachLoginEventHandlers(addToView = addToView, rootPane = rootPane, accountList = accountList, config = config)
}

fun attachLoginEventHandlers(newPane: () -> Pane = ::mainPane,
                             addToView: (Pane) -> Pane,
                             rootPane: BorderPane,
                             accountList: ObservableList<AccountState>,
                             config: Config,
                             oauthView: Viewer<OAuthStartEvent> = Viewer(
                                     controller = { oauthStartEvent ->
                                         OAuthController(
                                                 client = oauthStartEvent.client,
                                                 appRegistration = oauthStartEvent.appRegistration,
                                                 oAuthurl = getOAuthUrl(oauthStartEvent.client,
                                                         oauthStartEvent.appRegistration.clientId,
                                                         oauthStartEvent.appRegistration.redirectUri)
                                         )
                                     },
                                     template = "/oauth.fxml"
                             )) {
    // CREATE_ACCOUNT -> get server name -> SERVER_CONNECT -> OAUTH_START -> got to oauth page and get token -> OAUTH_TOKEN
    rootPane.addEventHandler(CreateAccountEvent.CREATE_ACCOUNT,
            { _ ->
                addToView(viewLoginForm(newPane()))
            })

    rootPane.addEventHandler(ServerConnectEvent.SERVER_CONNECT,
            { serverConnectEvent ->
                val client = createServerClient(serverConnectEvent.server)
                val appRegistration = registerApp(client)
                Event.fireEvent(serverConnectEvent.target,
                        OAuthStartEvent(source = serverConnectEvent.source,
                                target = serverConnectEvent.target,
                                appRegistration = appRegistration!!,
                                client = client))
            })

    rootPane.addEventHandler(OAUTH_START,
            { oauthStartEvent ->
                addToView(oauthView.view(newPane(), oauthStartEvent, ViewerMode.REPLACE))
            })

    rootPane.addEventHandler(OAUTH_TOKEN,
            { oAuthTokenEvent ->
                val appRegistration = oAuthTokenEvent.appRegistration
                val oldClient = oAuthTokenEvent.client
                val accessToken = getAccessToken(oAuthTokenEvent.client,
                        oAuthTokenEvent.appRegistration.clientId,
                        oAuthTokenEvent.appRegistration.clientSecret,
                        oAuthTokenEvent.token)
                logger.info("base url: " + oldClient.getInstanceName())
                val client = createAccountClient(oldClient.getInstanceName(), accessToken.accessToken)
                accountList.add(createAccountState(client))
                val accountConfig = createAccountConfig(
                        Accounts(client),
                        accessToken.accessToken,
                        appRegistration.clientId,
                        appRegistration.clientSecret,
                        client.getInstanceName())
                addAccountToConfig(config, accountConfig)
                //TODO: fire an event to view the feeds
            })
}