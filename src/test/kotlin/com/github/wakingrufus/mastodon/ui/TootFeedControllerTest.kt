package com.github.wakingrufus.mastodon.ui;

import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.ui.controllers.TootFeedController
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import mu.KLogging
import org.junit.Test
import org.testfx.api.FxAssert.verifyThat
import org.testfx.framework.junit.ApplicationTest
import org.testfx.matcher.base.NodeMatchers
import java.io.IOException

public class TootFeedControllerTest : ApplicationTest() {
    companion object : KLogging()

    @Test
    @Throws(IOException::class)
    fun test() {
        logger.info("TootFeedControllerTest")
        verifyThat<Node>("#tootFeedWrapper", NodeMatchers.isVisible())
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val account: Account = Account(displayName = "displayName")
        val status: Status = Status(account = account)
        val toots: ObservableList<Status> = FXCollections.observableArrayList(status)
        val tootController = TootFeedController(
                statuses = toots,
                accountPrompter = { AccountState(account = account, client = mock()) })
        val fxmlLoader = FXMLLoader(javaClass.getResource("/toot-feed.fxml"))
        fxmlLoader.setController(tootController)
        val load: Parent = fxmlLoader.load()
        val scene = Scene(load, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }
}