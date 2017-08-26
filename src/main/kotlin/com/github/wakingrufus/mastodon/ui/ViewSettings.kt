package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.controllers.SettingsController
import javafx.collections.ObservableList
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun viewSettings(parent: Pane, accountList: ObservableList<AccountState>) {
    Viewer<ObservableList<AccountState>>(
            controller = { accountStates -> SettingsController(accountStates = accountStates) },
            template = "/settings.fxml")
            .view(parent = parent, item = accountList, mode = ViewerMode.REPLACE)
}
