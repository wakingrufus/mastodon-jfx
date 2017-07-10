package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.MastodonClient


data class FeedElement(val query: FeedQuery? = null,
                       val client: MastodonClient) {
}