package com.github.wakingrufus.mastodon

import com.github.wakingrufus.mastodon.account.createAccountState
import com.github.wakingrufus.mastodon.client.createAccountClient
import com.github.wakingrufus.mastodon.client.createServerClient
import com.github.wakingrufus.mastodon.client.registerApp
import com.github.wakingrufus.mastodon.events.BoostStatusEvent
import com.github.wakingrufus.mastodon.events.OAuthStartEvent
import com.github.wakingrufus.mastodon.events.ServerConnectEvent
import com.github.wakingrufus.mastodon.toot.boostToot
import javafx.event.Event
import javafx.scene.layout.Pane

fun attachEventHandlers(rootPane: Pane) {
    rootPane.addEventHandler(BoostStatusEvent.BOOST_STATUS,
            { boostStatusEvent -> boostToot(boostStatusEvent.getStatusId(), boostStatusEvent.getAccount().client) })
}