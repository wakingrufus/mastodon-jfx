package com.github.wakingrufus.mastodon.ui.feeds;

import com.github.wakingrufus.mastodon.feed.FeedElement
import com.github.wakingrufus.mastodon.feed.FeedQuery
import com.github.wakingrufus.mastodon.feed.TootFeedState
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Status
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
        verifyThat<Node>("#displayName", NodeMatchers.hasText("displayName"))

        //   Node displayName = v.lookup("#displayName");
        //  assertEquals("displayName", displayName.getAccessibleText());
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val mastodonClient = mock<MastodonClient> {
            //   on { get(String()) } doReturn Response.Builder().build()
        }
        val feedState: TootFeedState = TootFeedState(elements = arrayListOf(FeedElement(FeedQuery.HOME, mastodonClient)))

        val account: Account = Account(displayName = "displayName")
        val status: Status = Status(account = account)
        feedState.addItem(status)
        val tootController = TootFeedController(feedState)
        val fxmlLoader = FXMLLoader(javaClass.getResource("/toot-feed.fxml"))
        fxmlLoader.setController(tootController)
        val load: Parent = fxmlLoader.load()
        val scene = Scene(load, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }
}