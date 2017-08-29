package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.controllers.FeedsController
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Hyperlink
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import javafx.stage.Stage
import mu.KLogging
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import kotlin.test.assertEquals

class ParseTootContentKtTest: ApplicationTest() {
    companion object : KLogging()

    val sampleToot1: String = "<p>Fishing</p><p>text before link <a href=\"https://www.gamingonlinux.com/articles/fishing-planet-leaves-early-access-with-full-linux-support-also-has-the-unity-fullscreen-bug.10242\" rel=\"nofollow noopener\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"ellipsis\">gamingonlinux.com/articles/fis</span><span class=\"invisible\">hing-planet-leaves-early-access-with-full-linux-support-also-has-the-unity-fullscreen-bug.10242</span></a> <a href=\"https://mastodon.social/tags/linux\" class=\"mention hashtag\" rel=\"tag\">#<span>Linux</span></a> <a href=\"https://mastodon.social/tags/linuxgaming\" class=\"mention hashtag\" rel=\"tag\">#<span>LinuxGaming</span></a></p>"
    val sampleToot2: String = "<p>RT <span class=\"h-card\"><a href=\"https://mastodon.social/@plsburydoughboy\" class=\"u-url mention\">@<span>plsburydoughboy</span></a></span> Is it gay to dive into the Marianas Trench you&apos;re entering a power bottom</p>\n"

    @Test
    fun parseToot() {
        val actual: Pane = parseToot(content = sampleToot1)
        logger.info { actual.toString() }
        val node: VBox = actual.children[0] as VBox
        logger.info { node.toString() }
        assertEquals(2, node.children.size, "2 paragraphs")
        val par1: TextFlow = node.children[0] as TextFlow
        val par1Text = par1.children[0] as Text
        assertEquals("Fishing", par1Text.text)
        val par2: TextFlow = node.children[1] as TextFlow
        //  assertEquals(4, node.children.size, "2nd paragraph has 4 children")
        val par2Text: Text = par2.children[0] as Text
        assertEquals("text before link ", par2Text.text)
        val par2link1: Hyperlink = par2.children[1] as Hyperlink
        assertEquals("https://www.gamingonlinux.com/articles/fishing-planet-leaves-early-access-with-full-linux-support-also-has-the-unity-fullscreen-bug.10242",
                par2link1.text)
        val par2Text2: Text = par2.children[2] as Text
        assertEquals(" ", par2Text2.text)
        val par2link2: Hyperlink = par2.children[3] as Hyperlink
        assertEquals("#Linux", par2link2.text)
        val par2Text3: Text = par2.children[4] as Text
        assertEquals(" ", par2Text3.text)
        val par2link3: Hyperlink = par2.children[5] as Hyperlink
        assertEquals("#LinuxGaming", par2link3.text)
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val load: Parent = HBox()
        val scene = Scene(load, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }

}