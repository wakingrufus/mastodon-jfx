package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.TornadoFxTest
import com.github.wakingrufus.mastodon.data.NotificationFeed
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import mu.KLogging
import org.junit.Test

class NotificationFeedViewTest : TornadoFxTest() {
    companion object : KLogging()

    @Test
    fun test() {
        val account = Account(
                id = 1,
                displayName = "displayName",
                userName = "username")
        showViewWithParams<NotificationFeedView>(mapOf(
                "notificationFeed" to NotificationFeed(
                        name = "userName",
                        server = "http://mastodon.social",
                        notifications = FXCollections.observableArrayList(
                                Notification(
                                        account = account,
                                        type = Notification.Type.Follow.value),
                                Notification(
                                        status = Status(
                                                content = "<p>toot</p>",
                                                account = account),
                                        account = account,
                                        type = Notification.Type.Favourite.value),
                                Notification(
                                        status = Status(
                                                content = "<p>toot</p>",
                                                account = account),
                                        account = account,
                                        type = Notification.Type.Mention.value),
                                Notification(
                                        status = Status(
                                                content = "<p>toot</p>",
                                                account = account),
                                        account = account,
                                        type = Notification.Type.Reblog.value)
                        )
                )
        ))
    }
}