package com.github.wakingrufus.mastodon.data

import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.ObservableList

data class NotificationFeed(val name: String, val server: String, val notifications: ObservableList<Notification>)