package com.github.wakingrufus.mastodon.toot

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Status
import com.sys1yagi.mastodon4j.api.method.Statuses

fun createToot(client: MastodonClient,
               statusesClient: () -> Statuses = { Statuses(client = client) },
               status: String,
               inReplyToId: Long?): Status {
    return statusesClient.invoke().postStatus(
            status = status,
            inReplyToId = inReplyToId,
            sensitive = false,
            spoilerText = null,
            mediaIds = null).execute()
}
