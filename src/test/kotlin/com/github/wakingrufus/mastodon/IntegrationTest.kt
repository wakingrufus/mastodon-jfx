package com.github.wakingrufus.mastodon

import com.github.wakingrufus.mastodon.config.ConfigData
import com.github.wakingrufus.mastodon.config.ConfigurationHandler
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import javafx.stage.Stage
import mu.KLogging
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest

class IntegrationTest : ApplicationTest() {

    companion object : KLogging()

    override fun start(stage: Stage) {
        val config: ConfigData = mock()
        val configHandler = mock<ConfigurationHandler> {
            on { readFileConfig() } doReturn config
        }
        MastodonApplication(configHandler = configHandler).start(stage)
    }

    @Test
    fun should_drag_file_into_trashcan() {
        /*
        // given:


        // when:
        clickOn("#newIdButton");

        // then:
        verifyThat("#desktop", hasChildren(0, ".file"));
        */
    }


}