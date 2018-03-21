package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.NotificationFeed
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.stage.StageStyle
import mu.KLogging
import tornadofx.*

class SettingsView(accountFragment: (String, Account) -> AccountFragment =
                           { s: String, a: Account -> find(params = mapOf("server" to s, "account" to a)) }) : View() {
    companion object : KLogging()

    val createAccount: () -> Unit by param()
    val accountStates: ObservableList<AccountState> by param()
    val viewFeed: (StatusFeed) -> Any by param()
    val viewNotifications: (NotificationFeed) -> Any by param()
    val newToot: () -> Any by param()
    override val root = vbox {
        style {
            minWidth = 30.em
            minHeight = 100.percent
            backgroundColor = multi(DefaultStyles.backdropColor)
            padding = CssBox(top = 1.px, right = 1.px, bottom = 1.px, left = 1.px)
        }
        vbox {
            style {
                id = "accountListWrapper"
                textFill = Color.WHITE
                minHeight = 100.percent
                backgroundColor = multi(DefaultStyles.backgroundColor)
            }
            children.bind(accountStates) {
                hbox {
                    vbox {
                        this += accountFragment(it.client.getInstanceName(), it.account)
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
                            button("📝") {
                                addClass(DefaultStyles.smallButton)
                                accessibleText = "New Toot"
                                action {
                                    find<TootEditor>(mapOf("client" to it.client)).apply {
                                        openModal(stageStyle = StageStyle.UTILITY, block = true)
                                    }
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