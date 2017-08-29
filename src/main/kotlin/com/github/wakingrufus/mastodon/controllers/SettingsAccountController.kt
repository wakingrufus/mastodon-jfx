package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.events.ViewFeedEvent
import com.github.wakingrufus.mastodon.events.ViewNotificationsEvent
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import mu.KLogging

class SettingsAccountController(private val accountState: AccountState,
                                private val accountViewer: Viewer<Account> = Viewer(
                                        controller = { account ->
                                            AccountController(
                                                    account = account,
                                                    server = accountState.client.getInstanceName())
                                        },
                                        template = "/account.fxml"))
    : Controller<AccountState> {
    companion object : KLogging()

    @FXML
    internal var homeFeedButton: Button? = null
    @FXML
    internal var publicFeedButton: Button? = null
    @FXML
    internal var federatedFeedButton: Button? = null
    @FXML
    internal var notificationFeedButton: Button? = null
    @FXML
    internal var accountView: HBox? = null

    @FXML
    override fun initialize() {
        if (accountView == null) {
            logger.error { "null account" }
        } else {
            accountViewer.view(parent = accountView!!, item = accountState.account, mode = ViewerMode.APPEND)
            homeFeedButton?.setOnAction { actionEvent ->
                Event.fireEvent(actionEvent.target,
                        ViewFeedEvent(actionEvent.source, actionEvent.target, accountState, accountState.homeFeed))
            }
            publicFeedButton?.setOnAction { actionEvent ->
                Event.fireEvent(actionEvent.target,
                        ViewFeedEvent(actionEvent.source, actionEvent.target, accountState, accountState.publicFeed))
            }
            federatedFeedButton?.setOnAction { actionEvent ->
                Event.fireEvent(actionEvent.target,
                        ViewFeedEvent(actionEvent.source, actionEvent.target, accountState, accountState.federatedFeed))
            }
            notificationFeedButton?.setOnAction { actionEvent ->
                Event.fireEvent(actionEvent.target,
                        ViewNotificationsEvent(actionEvent.source,
                                actionEvent.target,
                                accountState,
                                accountState.notificationFeed))
            }
        }
    }
}