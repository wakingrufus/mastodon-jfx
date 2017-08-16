package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.account.AccountState
import javafx.scene.control.ChoiceDialog
import javafx.scene.control.Dialog
import javafx.stage.StageStyle

fun buildAccountDialog(accounts: List<AccountState>): Dialog<AccountState> {
    val dialog = ChoiceDialog<AccountState>(accounts.get(0), accounts)
    dialog.contentText = "Choose account"
    dialog.initStyle(StageStyle.UTILITY)
    return dialog
}