package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.NotificationFeed
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import mu.KLogging

class SettingsController(private val createAccount: () -> Pane,
                         private val accountStates: ObservableList<AccountState>,
                         private val settingsAccountViewer: Viewer<AccountState> = Viewer(
                                 controller = { accountState ->
                                     SettingsAccountController(
                                             accountState = accountState,
                                             viewFeed = viewFeed,
                                             viewNotificationFeed = viewNotifications)
                                 },
                                 template = "/settings-account.fxml"),
                         private val viewFeed: (StatusFeed) -> Any,
                         private val viewNotifications: (NotificationFeed) -> Any)
    : Controller<ObservableList<AccountState>> {
    companion object : KLogging()

    @FXML
    internal var newIdButton: Button? = null

    @FXML
    fun handleNewIdButtonAction(event: ActionEvent) {
        createAccount.invoke()
    }

    @FXML
    internal var accountListWrapper: VBox? = null

    @FXML
    override fun initialize() {
        accountListWrapper?.children?.clear()
        accountStates.forEach {
            settingsAccountViewer.view(parent = accountListWrapper!!, item = it, mode = ViewerMode.APPEND)
        }
        accountStates.addListener { change: ListChangeListener.Change<out AccountState>? ->
            while (change?.next()!!) {
                change.addedSubList?.forEach {
                    settingsAccountViewer.view(parent = accountListWrapper!!, item = it, mode = ViewerMode.APPEND)
                }
            }
        }
    }
}