package com.github.wakingrufus.mastodon.data

import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList

data class StatusFeed(val name: String, val server: String, val statuses: ObservableList<Status>)