package com.github.wakingrufus.mastodon.ui.controllers

import com.github.wakingrufus.mastodon.ui.FittedWebView
import com.github.wakingrufus.mastodon.ui.viewAccount
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.fxml.FXML
import javafx.scene.layout.HBox

class TootController(private val status: Status,
                     private val viewAccountFunction: (HBox, Account) -> Unit = ::viewAccount) {

    @FXML
    internal var accountView: HBox? = null
    @FXML
    internal var content: FittedWebView? = null

    @FXML
    fun initialize() {
        viewAccountFunction(accountView!!, status.account!!)
        content!!.setContent(status.content)
        content!!.setHtmlStylesheetLocation(javaClass.getResource("/css/toot-content.css").toString())
    }

}