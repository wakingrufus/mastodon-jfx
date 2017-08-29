package com.github.wakingrufus.mastodon.controllers;

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
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
        verifyThat<Node>("#feedLabel", NodeMatchers.hasText("Test @ Server"))
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val account: Account = Account(displayName = "displayName")
        val status: Status = Status(account = account)
        val toots: ObservableList<Status> = FXCollections.observableArrayList(status)
        val statusFeed = StatusFeed(statuses = toots, name = "Test",server = "Server")
        val tootController = TootFeedController(
                statusFeed = statusFeed,
                accountPrompter = { AccountState(account = account, client = mock()) })
        val fxmlLoader = FXMLLoader(javaClass.getResource("/toot-feed.fxml"))
        fxmlLoader.setController(tootController)
        val load: Parent = fxmlLoader.load()
        val scene = Scene(load, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }
}