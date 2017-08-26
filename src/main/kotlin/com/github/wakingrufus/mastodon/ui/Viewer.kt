package com.github.wakingrufus.mastodon.ui

import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import mu.KLogging
import java.io.IOException


class Viewer<T>(private val controller: (item: T) -> Controller<T>, private val template: String) {
    companion object : KLogging()
    fun view(parent: Pane, item: T, mode: ViewerMode = ViewerMode.APPEND) : Pane{
        if (mode == ViewerMode.REPLACE) {
            parent.children.clear()
        }
        val controller = controller.invoke(item)
        try {
            val fxmlLoader = FXMLLoader(object : Any() {}.javaClass.getResource(template))
            fxmlLoader.setController(controller)
            if (mode == ViewerMode.PREPEND) {
                parent.children.add(element = fxmlLoader.load(), index = 0)
            } else {
                parent.children.add(fxmlLoader.load())
            }
        } catch (e: IOException) {
            logger.error("error loading account pane: " + e.localizedMessage, e)
        }
        return parent
    }
}