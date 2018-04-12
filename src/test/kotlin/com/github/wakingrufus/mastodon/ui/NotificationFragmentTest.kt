package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.TornadoFxTest
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Notification
import mu.KLogging
import org.junit.Test

class NotificationFragmentTest : TornadoFxTest() {
    companion object : KLogging()

    @Test
    fun test_follow() {
        showViewWithParams<NotificationFragment>(mapOf(
                "server" to "server",
                "notification" to Notification(
                        account = Account(
                                id = 1,
                                displayName = "displayName",
                                userName = "username"),
                        type = "follow")))
    }
}