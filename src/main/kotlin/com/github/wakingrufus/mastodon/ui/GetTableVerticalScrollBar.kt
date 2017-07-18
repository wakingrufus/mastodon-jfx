package com.github.wakingrufus.mastodon.ui

import javafx.geometry.Orientation
import javafx.scene.control.ScrollBar
import javafx.scene.control.TableView


fun getTableVerticalScrollbar(table: TableView<*>): ScrollBar? {
    val result: ScrollBar? = table.lookupAll(".scroll-bar")
            .filterIsInstance<ScrollBar>()
            .map { it as ScrollBar }
            .lastOrNull { it.orientation == Orientation.VERTICAL }
    return result
}