package com.github.wakingrufus.mastodon.feed

import com.sys1yagi.mastodon4j.api.Pageable
import com.sys1yagi.mastodon4j.api.Range
import com.sys1yagi.mastodon4j.api.entity.Status
import com.sys1yagi.mastodon4j.api.method.Timelines

public fun fetchHomeFeed(timelines: Timelines) :Pageable<Status>{
    return timelines.getHome(Range()).execute()
}