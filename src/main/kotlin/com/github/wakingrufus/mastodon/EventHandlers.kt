package com.github.wakingrufus.mastodon

import com.github.wakingrufus.mastodon.events.BoostStatusEvent
import com.github.wakingrufus.mastodon.toot.boostToot
import javafx.scene.layout.Pane

fun attachEventHandlers(rootPane: Pane) {
    rootPane.addEventHandler(BoostStatusEvent.BOOST_STATUS,
            { boostStatusEvent -> boostToot(boostStatusEvent.getStatusId(), boostStatusEvent.getAccount().client) })
}