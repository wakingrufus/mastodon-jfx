package com.github.wakingrufus.mastodon.ui.styles

import javafx.scene.Cursor
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import tornadofx.*


class DefaultStyles : Stylesheet() {
    companion object {
        val smallButton by cssclass()
        val textInputLabel by cssclass()
        val textInput by cssclass()
        val defaultBorder by cssclass()

        val backdropColor = c ("#222222")
        val backgroundColor = c("#122e43")
        val darkestBackgroundColor = c("#061018")
        val darkerBackgroundColor = c("#0E2333")
        val linkColor = c("#AAAAFF")

        private val presetBackgroundColor = c("#061018")
        private val armedBackgroundColor = c("#2b6a9b")
        private val hoverBackgroundColor = c("#122e43")
        val textColor = c("#2b6a9b")
        val armedTextColor = Color.WHITE

    }

    init {
        smallButton {
            backgroundColor = multi(presetBackgroundColor)
            textFill = textColor
            backgroundRadius = multi(CssBox(10.px, 10.px, 10.px, 10.px))
            backgroundInsets = multi(CssBox(0.px, 0.px, 0.px, 0.px))
            fontSize = 3.em
            cursor = Cursor.HAND
            and(armed) {
                backgroundColor = multi(armedBackgroundColor)
                textFill = armedTextColor
            }
            and(hover) {
                backgroundColor = multi(hoverBackgroundColor)
                textFill = textColor
            }
        }
        textInputLabel {
            fontSize = 2.em
            minWidth = 10.em
            maxWidth = 50.percent
        }
        textInputLabel {
            fontSize = 3.em
            minWidth = 10.em
            maxWidth = 50.percent
            textFill = Color.WHITE
        }
        defaultBorder {
            borderColor = multi(CssBox(
                    top = DefaultStyles.darkestBackgroundColor,
                    bottom = DefaultStyles.darkestBackgroundColor,
                    left = DefaultStyles.darkestBackgroundColor,
                    right = DefaultStyles.darkestBackgroundColor
            ))
            borderWidth = multi(CssBox(
                    top = .2.em,
                    bottom = .2.em,
                    left = .2.em,
                    right = .2.em))
            borderStyle = multi(BorderStrokeStyle.SOLID)
        }

    }
}