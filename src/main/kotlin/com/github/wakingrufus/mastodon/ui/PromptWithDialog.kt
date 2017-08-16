package com.github.wakingrufus.mastodon.ui

import javafx.scene.control.Dialog


fun <T> promptWithDialog(dialog: Dialog<T>): T? {
    return dialog.showAndWait().orElse(null)
}