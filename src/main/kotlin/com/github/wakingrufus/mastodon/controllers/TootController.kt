package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.toot.boostToot
import com.github.wakingrufus.mastodon.toot.unboostToot
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import com.github.wakingrufus.mastodon.ui.parseToot
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import mu.KLogging

class TootController(private val status: Status,
                     private val accountViewer: Viewer<Account> = Viewer(
                             controller = { item -> AccountController(account = item, server = status.account?.url) },
                             template = "/account.fxml"),
                     private val accountPrompter: () -> (AccountState?),
                     private val tootParser: (String) -> (Pane) = ::parseToot) : Controller<Status> {
    companion object : KLogging()

    @FXML
    internal var accountView: HBox? = null

    @FXML
    internal var parseContent: VBox? = null

    @FXML
    internal var favButton: Button? = null

    @FXML
    internal var boostButton: Button? = null

    @FXML
    override fun initialize() {
        accountViewer.view(parent = accountView!!, item = status.account!!, mode = ViewerMode.APPEND)
        parseContent?.children?.add(tootParser.invoke(status.content))
        if (status.isFavourited) {
            favButton!!.text = "★"
        } else {
            favButton!!.text = "☆"
        }
        if (status.isReblogged) {
            boostButton!!.text = "♻"
        } else {
            boostButton!!.text = "♲"
        }
        boostButton?.setOnAction { actionEvent ->
            val account: AccountState? = accountPrompter.invoke()
            logger.info { "account chosen: $account" }
            if (account != null) {
                if (status.isReblogged) {
                    boostButton!!.text = "♲"
                    unboostToot(id = status.id, client = account.client)
                } else {
                    boostButton!!.text = "♻"
                    boostToot(id = status.id, client = account.client)
                }
            }
        }
    }
}