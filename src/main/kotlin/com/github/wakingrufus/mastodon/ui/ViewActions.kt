package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.client.getOAuthUrl
import com.github.wakingrufus.mastodon.controllers.*
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.NotificationFeed
import com.github.wakingrufus.mastodon.data.OAuthModel
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import javafx.collections.ObservableList
import javafx.scene.layout.Pane
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}
fun viewLoginForm(parent:() ->  Pane, startOAuth: (AppRegistration, MastodonClient) -> Pane): Pane {
    return Viewer<Unit>(controller = { LoginController(startOAuth = startOAuth) },
            template = "/login.fxml")
            .view(parent = parent.invoke(), item = Unit, mode = ViewerMode.REPLACE)
}

fun viewOAuthForm(parent:() ->  Pane,
                  appRegistration: AppRegistration,
                  client: MastodonClient,
                  completeOAuth: (OAuthModel) -> Any): Pane {
    return Viewer<OAuthModel>(
            controller = { oAuthModel ->
                OAuthController(
                        completeOAuth = completeOAuth,
                        oAuthModel = oAuthModel,
                        oAuthurl = getOAuthUrl(oAuthModel.client,
                                oAuthModel.appRegistration.clientId,
                                oAuthModel.appRegistration.redirectUri)
                )
            },
            template = "/oauth.fxml"
    ).view(parent = parent.invoke(),
            item = OAuthModel(client = client, appRegistration = appRegistration),
            mode = ViewerMode.REPLACE)
}

fun viewAccountNotifications(parent:() ->  Pane,
                             notifications: NotificationFeed,
                             accountPrompter: () -> AccountState?) {
    Viewer<NotificationFeed>(
            controller = { n -> NotificationFeedController(notificationFeed = n, accountPrompter = accountPrompter) },
            template = "/notification-feed.fxml")
            .view(parent = parent.invoke(), item = notifications, mode = ViewerMode.REPLACE)
}

fun viewSettings(parent:() ->  Pane,
                 accountList: ObservableList<AccountState>,
                 createAccount: () -> Pane,
                 viewFeed: (StatusFeed) -> Any,
                 viewNotifications: (NotificationFeed) -> Any) {
    Viewer<ObservableList<AccountState>>(
            controller = { accountStates ->
                SettingsController(
                        accountStates = accountStates,
                        createAccount = createAccount,
                        viewFeed = viewFeed,
                        viewNotifications = viewNotifications)
            },
            template = "/settings.fxml")
            .view(parent = parent.invoke(), item = accountList, mode = ViewerMode.REPLACE)
}

fun viewAccountFeeds(parent:() ->  Pane,
                     feedStates: ObservableList<StatusFeed>,
                     accounts: List<AccountState>) {
    Viewer<ObservableList<StatusFeed>>(
            controller = { feeds ->
                FeedsController(
                        feedStates = feeds,
                        accountPrompter = { promptWithDialog(dialog = buildAccountDialog(accounts)) })
            },
            template = "/feeds.fxml")
            .view(parent = parent.invoke(), item = feedStates, mode = ViewerMode.REPLACE)
}
