package com.github.wakingrufus.mastodon.client

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.method.Timelines

fun buildTimelinesClient(client: MastodonClient) : Timelines{
    return Timelines(client)
}