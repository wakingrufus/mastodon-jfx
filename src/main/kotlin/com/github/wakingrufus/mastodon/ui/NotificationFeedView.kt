package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.data.NotificationFeed
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.paint.Color
import mu.KLogging
import tornadofx.*

class NotificationFeedView : View() {
    companion object : KLogging()

    val notificationFeed: NotificationFeed by param()

    override val root = hbox {
        style {
            minWidth = 100.percent
            minHeight = 100.percent
            backgroundColor = multi(Color.rgb(0x06, 0x10, 0x18))
            padding = CssBox(top = 1.px, right = 1.px, bottom = 1.px, left = 1.px)
        }
        vbox {
            style {
                backgroundColor = multi(Color.rgb(0x32, 0x8B, 0xDB),
                        Color.rgb(0x20, 0x7B, 0xCF),
                        Color.rgb(0x19, 0x73, 0xC9),
                        Color.rgb(0x0A, 0x65, 0xBF))
                textFill = Color.WHITE
                padding = box(1.px, 1.px, 1.px, 1.px)
                alignment = Pos.CENTER
                maxWidth = 30.em
            }
            label(notificationFeed.name + " @ " + notificationFeed.server) {
                textFill = Color.WHITE
                style {
                    padding = CssBox(1.px, 1.px, 1.px, 1.px)
                    fontSize = 3.em
                }
            }
            scrollpane {
                hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                vbox {
                    children.bind(notificationFeed.notifications) {
                        find<NotificationFragment>(params = mapOf(
                                "notification" to it,
                                "server" to notificationFeed.server)).root
                    }
                }
            }
        }

    }
}