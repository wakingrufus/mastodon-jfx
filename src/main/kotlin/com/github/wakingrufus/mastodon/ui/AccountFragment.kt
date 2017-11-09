package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import mu.KLogging
import tornadofx.*
import java.net.HttpURLConnection
import java.net.URL

class AccountFragment(val account: Account, val server: String) : Fragment() {
    companion object : KLogging()

    lateinit var avatar: ImageView
    override val root = hbox {
        style {
            backgroundColor = multi(DefaultStyles.backgroundColor)
        }
        avatar = imageview {
            fitHeight = 64.px.value
            fitWidth = 64.px.value
            image = Image(resources["/images/avatar-default.png"])
        }

        vbox {
            label(account.displayName) {
                id = "display-name"
                textFill = Color.WHITE
                style {
                    fontSize = 2.5.em
                }

            }
            label("@" + account.userName + "@" + server) {
                textFill = Color.WHITE
                style {
                    fontSize = 1.5.em
                }
            }
        }

    }

    init {
        loadAvatar()
    }

    fun loadAvatar() {
        runAsync {
            try {
                val url = URL(account.avatar)
                val httpcon: HttpURLConnection = url.openConnection() as HttpURLConnection
                httpcon.addRequestProperty("User-Agent", "Mozilla/4.0")
                Image(httpcon.inputStream)
            } catch (e: Exception) {
                logger.warn("error loading avatar: " + account.avatar, e)
                Image(resources["/images/avatar-default.png"])
            }
        } ui {
            avatar.image = it
        }
    }
}