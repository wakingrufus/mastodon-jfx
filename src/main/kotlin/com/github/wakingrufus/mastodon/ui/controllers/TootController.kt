package com.github.wakingrufus.mastodon.ui.controllers

import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.toot.boostToot
import com.github.wakingrufus.mastodon.toot.unboostToot
import com.github.wakingrufus.mastodon.ui.Controller
import com.github.wakingrufus.mastodon.ui.FittedWebView
import com.github.wakingrufus.mastodon.ui.Viewer
import com.github.wakingrufus.mastodon.ui.ViewerMode
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import mu.KLogging

class TootController(private val status: Status,
                     private val accountViewer: Viewer<Account> = Viewer(
                             controller = { item -> AccountController(item) },
                             template = "/account.fxml"),
                     private val accountPrompter: () -> (AccountState?)) : Controller<Status> {
    companion object : KLogging()

    @FXML
    internal var accountView: HBox? = null

    @FXML
    internal var content: FittedWebView? = null

    @FXML
    internal var favButton: Button? = null

    @FXML
    internal var boostButton: Button? = null

    @FXML
    override fun initialize() {
        accountViewer.view(parent = accountView!!, item = status.account!!, mode = ViewerMode.APPEND)
        content!!.setContent(status.content)
        content!!.setHtmlStylesheetLocation(javaClass.getResource("/css/toot-content.css").toString())
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