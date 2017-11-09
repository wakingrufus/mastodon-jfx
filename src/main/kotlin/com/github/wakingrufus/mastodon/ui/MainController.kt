package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.account.createAccountState
import com.github.wakingrufus.mastodon.client.createAccountClient
import com.github.wakingrufus.mastodon.config.ConfigurationHandler
import com.github.wakingrufus.mastodon.config.FileConfigurationHandler
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import javafx.collections.FXCollections
import javafx.scene.layout.Pane
import tornadofx.Controller
import java.io.File


class MainController(val configHandler: ConfigurationHandler =
                     FileConfigurationHandler(File(File(System.getProperty("user.home")), ".mastodon.txt"))): Controller(){
    val accountList = FXCollections.observableArrayList<AccountState>()
    val feeds = FXCollections.observableArrayList<StatusFeed>()

    fun readConfig(){
        configHandler.readFileConfig().identities.forEach { (accessToken, _, _, _, server) ->
            val client = createAccountClient(server, accessToken)
            accountList.add(createAccountState(client))
        }
    }
}