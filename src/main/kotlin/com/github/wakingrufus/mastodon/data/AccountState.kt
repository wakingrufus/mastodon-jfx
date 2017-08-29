package com.github.wakingrufus.mastodon.data

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList

data class AccountState(val account: Account,
                        val client: MastodonClient,
                        val homeFeed: StatusFeed = StatusFeed(
                                name = "Home",
                                server = client.getInstanceName(),
                                statuses = FXCollections.observableArrayList<Status>()),
                        val publicFeed: StatusFeed = StatusFeed(
                                name = "Public",
                                server = client.getInstanceName(),
                                statuses = FXCollections.observableArrayList<Status>()),
                        val federatedFeed: StatusFeed = StatusFeed(
                                name = "Federated",
                                server = client.getInstanceName(),
                                statuses = FXCollections.observableArrayList<Status>()),
                        val notificationFeed: ObservableList<Notification> = FXCollections.observableArrayList<Notification>()) {
    override fun toString(): String {
        return account.displayName + "@" + client.getInstanceName()
    }
}