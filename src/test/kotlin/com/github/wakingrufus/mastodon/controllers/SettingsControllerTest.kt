package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import mu.KLogging
import okhttp3.OkHttpClient
import org.junit.Test
import org.testfx.api.FxAssert.verifyThat
import org.testfx.framework.junit.ApplicationTest
import org.testfx.matcher.base.NodeMatchers
import java.io.IOException

public class SettingsControllerTest : ApplicationTest() {
    companion object : KLogging()

    @Test
    @Throws(IOException::class)
    fun test() {
        logger.info("FeedsControllerTest")
        verifyThat<Node>("#accountListWrapper", NodeMatchers.isVisible())
        verifyThat<Node>("#newIdButton", NodeMatchers.hasText("Add"))
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val accounts: ObservableList<AccountState> = FXCollections.observableArrayList()
        val account: Account = Account(id = 1L,
                userName = "name",
                displayName = "displayName")
        val accountState: AccountState = AccountState(account = account,
                client = MastodonClient.Builder(
                        instanceName = "instanceName",
                        gson = Gson(),
                        okHttpClientBuilder = OkHttpClient.Builder())
                        .accessToken("test").build())
        accounts.add(accountState)
        val instance = SettingsController(
                accountStates = accounts,
                createAccount = { mock() },
                viewFeed = { mock() },
                viewNotifications = { mock() })
        val fxmlLoader = FXMLLoader(javaClass.getResource("/settings.fxml"))
        fxmlLoader.setController(instance)
        val load: Parent = fxmlLoader.load()
        val scene = Scene(load, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }
}