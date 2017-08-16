package com.github.wakingrufus.mastodon.ui.controllers

import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import mu.KLogging


class NotificationController(private val notification: Notification,
                             private val accountPrompter: () -> AccountState?,
                             private val statusViewer: Viewer<Status> = Viewer(
                                     controller = { item -> TootController(status = item, accountPrompter = accountPrompter) },
                                     template = "/toot.fxml"),
                             private val accountViewer: Viewer<Account> = Viewer(
                                     controller = { account -> AccountController(account) },
                                     template = "/account.fxml")) {
    companion object : KLogging()

    @FXML
    internal var displayName: Label? = null
    @FXML
    internal var notificationTime: Label? = null
    @FXML
    internal var message: Label? = null
    @FXML
    internal var accountView: HBox? = null
    @FXML
    internal var tootView: HBox? = null

    @FXML
    fun initialize() {
        displayName?.text = notification.account!!.displayName
        if (notification.type.equals("follow", ignoreCase = true)) {
            message?.text = "followed you."
            accountViewer.view(accountView!!, notification.account!!)
        } else if (notification.type.equals("favourite", ignoreCase = true)) {
            message?.text = "has favourited your status"
            statusViewer.view(parent = tootView!!, item = notification.status!!, mode = ViewerMode.APPEND)
        } else {
            message?.text = notification.type
        }
        notificationTime?.text = notification.createdAt
    }
}