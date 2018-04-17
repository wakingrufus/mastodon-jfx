package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import com.sys1yagi.mastodon4j.api.entity.Notification
import javafx.geometry.Pos
import javafx.scene.paint.Color
import mu.KLogging
import tornadofx.*

class NotificationFragment : Fragment() {
    companion object : KLogging()

    val server: String by param()
    val notification: Notification by param()

    override val root = vbox {
        hbox {
            style {
                backgroundColor = multi(DefaultStyles.backgroundColor)
            }
            label(notification.account?.displayName!!) {
                textFill = Color.WHITE
                style {
                    padding = CssBox(1.px, 1.px, 1.px, 1.px)
                    fontSize = 1.5.em
                }
            }
            if (notification.type == Notification.Type.Follow.value) {
                label(" followed you.") {
                    textFill = Color.WHITE
                    style {
                        padding = CssBox(1.px, 1.px, 1.px, 1.px)
                        fontSize = 1.5.em
                    }
                }
            } else if (notification.type == Notification.Type.Reblog.value) {
                label(" boosted your toot.") {
                    textFill = Color.WHITE
                    style {
                        padding = CssBox(1.px, 1.px, 1.px, 1.px)
                        fontSize = 1.5.em
                    }
                }
            } else if (notification.type == Notification.Type.Favourite.value) {
                label(" favourited your toot") {
                    textFill = Color.WHITE
                    style {
                        padding = CssBox(1.px, 1.px, 1.px, 1.px)
                        fontSize = 1.5.em
                    }
                }
            } else if (notification.type == Notification.Type.Mention.value) {
                label(" mentioned you") {
                    textFill = Color.WHITE
                    style {
                        padding = CssBox(1.px, 1.px, 1.px, 1.px)
                        fontSize = 1.5.em
                    }
                }
            }
        }
        hbox {
            if (notification.type == Notification.Type.Follow.value
                    || notification.type == Notification.Type.Mention.value)
                this += find<AccountFragment>(params = mapOf(
                        "account" to notification.account,
                        "server" to server))
            else {
                addClass(DefaultStyles.defaultBorder)
                style {
                    padding = CssBox(top = 1.em, bottom = 1.em, left = 1.em, right = 1.em)
                    alignment = Pos.TOP_LEFT
                    backgroundColor = multi(DefaultStyles.backgroundColor)
                    textFill = Color.WHITE
                }
                val toot = parseToot(notification.status?.content!!)
                toot.style {
                    backgroundColor = multi(DefaultStyles.backgroundColor)
                    textFill = Color.WHITE
                }
                this += toot
            }
        }
        label(notification.createdAt) {
            textFill = Color.WHITE
            style {
                padding = CssBox(1.px, 1.px, 1.px, 1.px)
                fontSize = 1.em
            }
        }
    }
}