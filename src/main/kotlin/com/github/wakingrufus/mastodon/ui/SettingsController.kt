package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.events.CreateAccountEvent
import com.github.wakingrufus.mastodon.ui.settings.SettingsAccountController
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import mu.KLogging
import java.io.IOException

class SettingsController(private val accountStates: ObservableList<AccountState>) {
    companion object : KLogging()

    @FXML
    internal var newIdButton: Button? = null

    @FXML
    fun handleNewIdButtonAction(event: ActionEvent) {
        Event.fireEvent(newIdButton, CreateAccountEvent())
    }

    @FXML
    internal var accountListWrapper: VBox? = null

    fun initialize() {
        accountStates.forEach {
            val tootController = SettingsAccountController(it)
            val fxmlLoader = FXMLLoader(javaClass.getResource("/settings-account.fxml"))
            fxmlLoader.setController(tootController)
            try {
                accountListWrapper?.children?.add(fxmlLoader.load())
            } catch (e: IOException) {
                logger.error("error loading feed view: " + e.localizedMessage, e)
            }
        }
        accountStates.addListener { change: ListChangeListener.Change<out AccountState>? ->
            while (change?.next()!!) {
                change.addedSubList?.forEach { accountListWrapper?.children?.add(element = buildAccountPanel(it), index = 0) }
            }
        }
    }

    private fun buildAccountPanel(accountState: AccountState): HBox {
        val tootController = SettingsAccountController(accountState)
        val fxmlLoader = FXMLLoader(javaClass.getResource("/settings-account.fxml"))
        fxmlLoader.setController(tootController)
        return fxmlLoader.load()
    }
}