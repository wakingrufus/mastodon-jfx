package com.github.wakingrufus.mastodon.ui.controllers

import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.events.CreateAccountEvent
import com.github.wakingrufus.mastodon.ui.Controller
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import mu.KLogging

class SettingsController(private val accountStates: ObservableList<AccountState>,
                         private val settingsAccountViewer: Viewer<AccountState> = Viewer(
                                 controller = { accountState -> SettingsAccountController(accountState = accountState) },
                                 template = "/settings-account.fxml")) : Controller<ObservableList<AccountState>> {
    companion object : KLogging()

    @FXML
    internal var newIdButton: Button? = null

    @FXML
    fun handleNewIdButtonAction(event: ActionEvent) {
        Event.fireEvent(newIdButton, CreateAccountEvent())
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