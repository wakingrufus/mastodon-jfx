package com.github.wakingrufus.mastodon.client

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.method.Notifications

fun buildNotificationsClient(client: MastodonClient): Notifications {
    return Notifications(client)
}