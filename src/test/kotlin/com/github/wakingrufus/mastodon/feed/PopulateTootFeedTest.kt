package com.github.wakingrufus.mastodon.feed

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.MastodonRequest
import com.sys1yagi.mastodon4j.api.Dispatcher
import com.sys1yagi.mastodon4j.api.Pageable
import com.sys1yagi.mastodon4j.api.Shutdownable
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.embed.swing.JFXPanel
import mu.KLogging
import org.junit.Test
import kotlin.test.assertEquals

class PopulateTootFeedTest {
    companion object : KLogging()

    @Test
    fun populateTootFeed() {
        JFXPanel()
        val request = mock<MastodonRequest<Pageable<Status>>> {
            onGeneric { execute() } doReturn Pageable(part = listOf(Status()), link = null)
        }

        val feed: ObservableList<Status> = FXCollections.observableArrayList()

        populateTootFeed(
                feed = feed,
                listener = { Shutdownable(Dispatcher()) },
                fetcher = { request })

        assertEquals(1, feed.size)
    }
}