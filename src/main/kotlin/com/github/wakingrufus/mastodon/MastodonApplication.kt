package com.github.wakingrufus.mastodon

import com.github.wakingrufus.mastodon.account.createAccountConfig
import com.github.wakingrufus.mastodon.account.createAccountState
import com.github.wakingrufus.mastodon.client.createAccountClient
import com.github.wakingrufus.mastodon.client.getAccessToken
import com.github.wakingrufus.mastodon.config.ConfigurationHandler
import com.github.wakingrufus.mastodon.config.FileConfigurationHandler
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.github.wakingrufus.mastodon.ui.*
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import com.sys1yagi.mastodon4j.api.method.Accounts
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.text.Text
import javafx.stage.Stage
import mu.KLogging
import java.io.File
import java.io.IOException

class MastodonApplication(val configHandler: ConfigurationHandler =
                          FileConfigurationHandler(File(File(System.getProperty("user.home")), ".mastodon.txt")))
    : Application() {
    companion object : KLogging()

    override fun start(stage: Stage) {
        val accountList = FXCollections.observableArrayList<AccountState>()
        val feeds = FXCollections.observableArrayList<StatusFeed>()

        val rootEm = Math.rint(Text().layoutBounds.height)

        val mainView = MainView(rootEm = rootEm)

        val scene = Scene(mainView.root, rootEm * 80, rootEm * 60)

        // Set the application icon.
        stage.icons.add(Image(this.javaClass.getResourceAsStream("/images/avatar-default.png")))
        stage.title = "mastodon-jfx"
        stage.scene = scene
        stage.show()

        viewAccountFeeds(mainView::newMainView, feeds, accountList)

        configHandler.readFileConfig().identities.forEach { (accessToken, _, _, _, server) ->
            val client = createAccountClient(server, accessToken)
            accountList.add(createAccountState(client))
        }

        viewSettings(parent = mainView::newSettingsView,
                accountList = accountList,
                viewNotifications = { notificationFeed ->
                    viewAccountNotifications(
                            parent =mainView::newNotificationView,
                            notifications = notificationFeed,
                            accountPrompter = { promptWithDialog(dialog = buildAccountDialog(accountList)) })
                },
                viewFeed = { statusFeed -> feeds.add(statusFeed) },
                createAccount = {
                    viewLoginForm(mainView::newMainView, { appRegistration: AppRegistration, client: MastodonClient ->
                        viewOAuthForm(parent = mainView::newMainView,
                                appRegistration = appRegistration,
                                client = client,
                                completeOAuth = { (client1, appRegistration1, token) ->
                                    val accessToken = getAccessToken(client1,
                                            appRegistration1.clientId,
                                            appRegistration1.clientSecret,
                                            token!!)
                                    val accountClient = createAccountClient(
                                            instance = client1.getInstanceName(),
                                            accessToken = accessToken.accessToken)
                                    accountList.add(createAccountState(accountClient))
                                    configHandler.saveConfig(configHandler.addAccountToConfig(
                                            configHandler.readFileConfig(),
                                            createAccountConfig(
                                                    Accounts(accountClient),
                                                    accessToken.accessToken,
                                                    appRegistration.clientId,
                                                    appRegistration.clientSecret,
                                                    accountClient.getInstanceName())))
                                    viewAccountFeeds(mainView::newMainView, feeds, accountList)
                                })
                    })
                })
    }
}
