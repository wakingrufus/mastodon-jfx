package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.TornadoFxTest
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.api.entity.Status
import mu.KLogging
import org.junit.Test
import kotlin.test.assertEquals

class TootEditorTest : TornadoFxTest() {
    companion object : KLogging()

    @Test
    fun render() {
        val mockClientCall: (String) -> Status = { textBoxString: String ->
            Status(content = textBoxString)
        }
        val tootEditor = showViewWithParams<TootEditor>(mapOf(
                "client" to mock { },
                "toot" to mockClientCall,
                "inReplyToId" to null))

        clickOn("#toot-editor").write("test")
        clickOn("#toot-submit")

        assertEquals(
                expected = "test",
                actual = tootEditor.createdToot?.content)
    }
}
