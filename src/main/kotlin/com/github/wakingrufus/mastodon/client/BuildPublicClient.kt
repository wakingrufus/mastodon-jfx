package com.github.wakingrufus.mastodon.client

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.method.Public

fun buildPublicClient(client: MastodonClient): Public {
    return Public(client)
}