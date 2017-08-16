package com.github.wakingrufus.mastodon.toot

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.method.Statuses

fun boostToot(id: Long,
              client: MastodonClient,
              statusesClient: () -> Statuses = { Statuses(client = client) }) {
    statusesClient.invoke().postReblog(id).execute()
}

fun unboostToot(id: Long,
                client: MastodonClient,
                statusesClient: () -> Statuses = { Statuses(client = client) }) {
    statusesClient.invoke().postUnreblog(id).execute()
}