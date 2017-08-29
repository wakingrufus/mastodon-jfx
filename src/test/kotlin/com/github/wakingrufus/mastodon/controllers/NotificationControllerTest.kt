package com.github.wakingrufus.mastodon.controllers

import com.github.wakingrufus.mastodon.data.AccountState
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import mu.KLogging
import org.junit.Test
import org.testfx.api.FxAssert
import org.testfx.framework.junit.ApplicationTest
import org.testfx.matcher.base.NodeMatchers
import java.io.IOException


class NotificationControllerTest : ApplicationTest() {
    companion object : KLogging()

    val account: Account = Account(displayName = "displayName")
    val notificationUnknownType: Notification = Notification(account = account, type = "type", createdAt = "createdAt")
    val notificationFollow: Notification = Notification(account = account, type = "follow", createdAt = "createdAt")

    @Test
    @Throws(IOException::class)
    fun test() {
        logger.info("TootControllerTest")
        FxAssert.verifyThat<Node>("#accountView", NodeMatchers.isVisible())
        FxAssert.verifyThat<Node>("#notificationTime", NodeMatchers.hasText("createdAt"))
        FxAssert.verifyThat<Node>("#message", NodeMatchers.hasText("type"))
        FxAssert.verifyThat<Node>(lookup("#message").nth(1), NodeMatchers.hasText("followed you."))
        FxAssert.verifyThat<Node>("#displayName", NodeMatchers.hasText("displayName"))
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val tootController = NotificationController(
                notification = notificationUnknownType,
                accountPrompter = { AccountState(account = account, client = mock()) })
        val followController = NotificationController(
                notification = notificationFollow,
                accountPrompter = { AccountState(account = account, client = mock()) })
        val fxmlLoader = FXMLLoader(javaClass.getResource("/notification.fxml"))
        fxmlLoader.setController(tootController)
        val followLoader = FXMLLoader(javaClass.getResource("/notification.fxml"))
        followLoader.setController(followController)
        val parent: StackPane = StackPane()
        parent.children.addAll(fxmlLoader.load<Node>(), followLoader.load<Node>())
        val scene = Scene(parent, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }
}