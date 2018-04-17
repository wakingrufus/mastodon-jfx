package com.github.wakingrufus.mastodon.account

import com.github.wakingrufus.mastodon.waitFor
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.MastodonRequest
import com.sys1yagi.mastodon4j.api.Pageable
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.embed.swing.JFXPanel
import org.junit.Test

class CreateAccountStateKtTest {

    @Test
    fun createAccountState() {
    }

    @Test
    fun monitorPublicFeed() {
    }

    @Test
    fun monitorUserFeeds() {
    }

    @Test
    fun test_fetchAndAddToFeed() {
        JFXPanel()
        val request = mock<MastodonRequest<Pageable<Status>>> {
            onGeneric { execute() } doReturn Pageable(part = listOf(Status()), link = null)
        }

        val feed: ObservableList<Status> = FXCollections.observableArrayList()

        fetchAndAddToFeed(
                feed = feed,
                fetcher = { request })
        waitFor({ feed.size > 0 })
        kotlin.test.assertEquals(1, feed.size)
    }
}