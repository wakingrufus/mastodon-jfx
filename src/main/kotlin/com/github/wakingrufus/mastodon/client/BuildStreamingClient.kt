package com.github.wakingrufus.mastodon.client

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.method.Streaming

fun buildStreamingClient(client: MastodonClient): Streaming {
    return Streaming(client)
}