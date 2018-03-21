package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.client.parseUrl
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.github.wakingrufus.mastodon.toot.boostToot
import com.github.wakingrufus.mastodon.toot.unboostToot
import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.paint.Color
import javafx.stage.StageStyle
import mu.KLogging
import tornadofx.*

class StatusFeedsView : View() {
    companion object : KLogging()

    val statusFeeds: ObservableList<StatusFeed> by param()
    val accounts: ObservableList<AccountState> by param()
    val parseUrlFunc: (String) -> String by param(defaultValue = ::parseUrl)

    override val root = hbox {
        style {
            minWidth = 100.percent
            minHeight = 100.percent
            backgroundColor = multi(DefaultStyles.backdropColor)
            padding = CssBox(top = 1.px, right = 1.px, bottom = 1.px, left = 1.px)
        }
        children.bind(statusFeeds) {
            vbox {
                style {
                    backgroundColor = multi(DefaultStyles.backdropColor)
                    textFill = Color.WHITE
                    padding = box(1.px, 1.px, 1.px, 1.px)
                    alignment = Pos.CENTER
                    maxWidth = 40.em
                }
                label(it.name + " @ " + it.server) {
                    textFill = Color.WHITE
                    style {
                        backgroundColor = multi(DefaultStyles.backdropColor)
                        padding = CssBox(1.px, 1.px, 1.px, 1.px)
                        fontSize = 2.5.em
                    }
                }
                scrollpane {
                    hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                    vbox {
                        children.bind(it.statuses) { status ->
                            vbox {
                                addClass(DefaultStyles.defaultBorder)
                                style {
                                    padding = CssBox(top = 1.em, bottom = 1.em, left = 1.em, right = 1.em)
                                    alignment = Pos.TOP_LEFT
                                    backgroundColor = multi(DefaultStyles.backgroundColor)
                                    textFill = Color.WHITE
                                }
                                this += find<AccountFragment>(mapOf(
                                        "account" to status.account!!,
                                        "server" to parseUrlFunc(status.uri)))
                                val toot = parseToot(status.content)
                                toot.style {
                                    backgroundColor = multi(DefaultStyles.backgroundColor)
                                    textFill = Color.WHITE
                                }
                                this += toot
                                hbox {
                                    button("↰") {
                                        addClass(DefaultStyles.smallButton)
                                        action {
                                            val modal: AccountChooserView =
                                                    find<AccountChooserView>(mapOf("accounts" to accounts)).apply {
                                                        openModal(
                                                                stageStyle = StageStyle.UTILITY,
                                                                block = true)
                                                    }
                                            val account = modal.getAccount()
                                            logger.info { "account chosen: $account" }
                                            if (account != null) {
                                                find<TootEditor>(mapOf(
                                                        "client" to account.client,
                                                        "inReplyTo" to status)).apply {
                                                    openModal(stageStyle = StageStyle.UTILITY, block = true)
                                                }
                                            }

                                        }
                                    }
                                    button("☆") {
                                        if (status.isFavourited) text = "★"
                                        addClass(DefaultStyles.smallButton)
                                    }
                                    button("♲") {
                                        addClass(DefaultStyles.smallButton)
                                        if (status.isReblogged) text = "♻"
                                        action {
                                            val modal: AccountChooserView =
                                                    find<AccountChooserView>(mapOf("accounts" to accounts)).apply {
                                                        openModal(
                                                                stageStyle = StageStyle.UTILITY,
                                                                block = true)
                                                    }
                                            val account = modal.getAccount()
                                            logger.info { "account chosen: $account" }
                                            if (account != null) {
                                                if (status.isReblogged) {
                                                    this.text = "♲"
                                                    unboostToot(id = status.id, client = account.client)
                                                } else {
                                                    this.text = "♻"
                                                    boostToot(id = status.id, client = account.client)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}