package com.github.wakingrufus.mastodon.ui

import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane

fun addToMainView(parent: BorderPane, pane: Pane): Pane{
    parent.center = pane
    return pane
}