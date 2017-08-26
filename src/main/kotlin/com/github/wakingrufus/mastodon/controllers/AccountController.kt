package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.ui.Controller
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import mu.KLogging
import java.net.HttpURLConnection
import java.net.URL

class AccountController(private val account: Account) : Controller<Account> {
    companion object : KLogging()

    @FXML
    internal var accountName: Label? = null
    @FXML
    internal var serverName: Label? = null
    @FXML
    internal var avatar: Image? = null
    @FXML
    internal var avatarView: ImageView? = null

    @FXML
    override fun initialize() {
        accountName?.text = account.displayName
        var sb = StringBuilder()
        sb = sb.append("@")
        sb = sb.append(account.acct)

        serverName?.text = sb.toString()

        if (!account.avatar.isEmpty()) {
            if(!account.avatar.equals("/avatars/original/missing.png")) {
                launch(CommonPool) {
                    val url = URL(account.avatar)
                    val httpcon: HttpURLConnection = url.openConnection() as HttpURLConnection
                    httpcon.addRequestProperty("User-Agent", "Mozilla/4.0")
                    avatarView?.image = Image(httpcon.inputStream)
                }
            }
        }
    }
}