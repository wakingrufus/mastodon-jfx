package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.controllers.LoginController
import javafx.scene.layout.Pane
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun viewLoginForm(parent: Pane) : Pane {
  return  Viewer<Unit>(controller = { LoginController() },
            template = "/login.fxml")
            .view(parent = parent, item = Unit, mode = ViewerMode.REPLACE)
}
