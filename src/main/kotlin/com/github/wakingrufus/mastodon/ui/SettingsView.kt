package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.NotificationFeed
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.paint.Color
import mu.KLogging
import tornadofx.*

class SettingsView : View() {
    companion object : KLogging()

    val createAccount: () -> Unit by param()
    val accountStates: ObservableList<AccountState> by param()
    val viewFeed: (StatusFeed) -> Any by param()
    val viewNotifications: (NotificationFeed) -> Any by param()
    override val root = vbox {
        style {
            minWidth = 30.em
            minHeight = 100.percent
            backgroundColor = multi(Color.rgb(0x22, 0x22, 0x22))
            padding = CssBox(top = 1.px, right = 1.px, bottom = 1.px, left = 1.px)
        }
        vbox {
            style {
                id = "accountListWrapper"
                textFill = Color.WHITE
                minHeight = 100.percent
                backgroundColor = multi(Color.rgb(0x12, 0x2e, 0x43))
            }
            children.bind(accountStates) {
                hbox {
                    vbox {
                        this += AccountFragment(account = it.account, server = it.client.getInstanceName())
                        hbox {
                            button("⌂") {
                                addClass(DefaultStyles.smallButton)
                                accessibleText = "Home"
                                action {
                                    viewFeed(it.homeFeed)
                                }
                            }
                            button("👥") {
                                addClass(DefaultStyles.smallButton)
                                accessibleText = "Public"
                                action {
                                    viewFeed(it.publicFeed)
                                }
                            }
                            button("🌎") {
                                addClass(DefaultStyles.smallButton)
                                accessibleText = "Federated"
                                action {
                                    viewFeed(it.federatedFeed)
                                }
                            }
                            button("🔔") {
                                addClass(DefaultStyles.smallButton)
                                accessibleText = "Notifications"
                                action {
                                    viewNotifications(it.notificationFeed)
                                }
                            }
                        }
                    }
                }
            }
            hbox {
                style {
                    alignment = Pos.BOTTOM_CENTER
                    maxHeight = 2.em
                    minWidth = 100.percent
                }
                button {
                    addClass(DefaultStyles.smallButton)
                    text = "Add"
                    setOnAction { createAccount() }
                }
            }
        }

    }
}