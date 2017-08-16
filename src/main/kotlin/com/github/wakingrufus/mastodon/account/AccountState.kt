package com.github.wakingrufus.mastodon.account

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.collections.FXCollections
import javafx.collections.ObservableList

data class AccountState(val account: Account,
                        val client: MastodonClient,
                        val homeFeed: ObservableList<Status> = FXCollections.observableArrayList<Status>(),
                        val publicFeed: ObservableList<Status> = FXCollections.observableArrayList<Status>(),
                        val federatedFeed: ObservableList<Status> = FXCollections.observableArrayList<Status>(),
                        val notificationFeed: ObservableList<Notification> = FXCollections.observableArrayList<Notification>()) {
    override fun toString(): String {
        return account.displayName + client.getInstanceName()
    }
}