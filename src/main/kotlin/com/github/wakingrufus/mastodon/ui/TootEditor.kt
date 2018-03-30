package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.client.parseUrl
import com.github.wakingrufus.mastodon.toot.createToot
import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.scene.paint.Color
import mu.KLogging
import tornadofx.*

class TootEditor : Fragment() {
    companion object : KLogging()

    val client: MastodonClient by param()
    val inReplyTo: Status? by param()
    val toot: (String) -> Status by param({ tootString ->
        createToot(
                client = client,
                status = tootString,
                inReplyToId = inReplyTo?.id)
    })
    val parseUrlFunc: (String) -> String by param(defaultValue = ::parseUrl)

    var createdToot: Status? = null


    override val root = vbox {
        style {
            backgroundColor = multi(DefaultStyles.backgroundColor)
        }
        inReplyTo?.let {
            label("Replying to:") {
                style {
                    fontSize = 2.em
                    minWidth = 11.em
                    padding = CssBox(1.px, 1.px, 1.px, 1.px)
                    textFill = DefaultStyles.armedTextColor
                }
            }
            this += find<AccountFragment>(mapOf(
                    "account" to it.account!!,
                    "server" to parseUrlFunc(it.uri)))
            val toot = parseToot(it.content)
            toot.style {
                backgroundColor = multi(DefaultStyles.backgroundColor)
                textFill = Color.WHITE
            }
            this += toot
        }
        val tootText = textarea {
            id = "toot-editor"
        }
        buttonbar {
            button("Toot") {
                addClass(DefaultStyles.smallButton)
                id = "toot-submit"
                action {
                    createdToot = toot(tootText.text)
                    close()
                }
            }
            button("Close") {
                addClass(DefaultStyles.smallButton)
                action {
                    close()
                }
            }
        }
    }


}