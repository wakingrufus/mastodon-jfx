package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.ui.styles.DefaultStyles
import javafx.scene.Node
import javafx.scene.control.Hyperlink
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import tornadofx.multi
import tornadofx.style
import tornadofx.tooltip
import java.awt.Desktop
import java.net.URI

private val logger = KotlinLogging.logger {}
fun parseToot(content: String): Pane {
    //  logger.info { content }
    val tootContainer = VBox()
    val document: Document = Jsoup.parse(content)
    val body: Element = document.body()
    tootContainer.children.add(parseNode(body))
    return tootContainer
}

fun parseNode(htmlNode: org.jsoup.nodes.Node): Node {
    var node: Node? = null
    //  logger.info { "${htmlNode.nodeName()} has ${htmlNode.childNodes().size} children" }

    if (htmlNode is Element && (htmlNode).tagName() == "a") {
        // logger.debug { "processing <a>: ${htmlNode.text()}" }
        // logger.debug { "href: ${htmlNode.attr("href")}" }
        val link = Hyperlink(htmlNode.text())
        link.style {
            fill = DefaultStyles.linkColor
            textFill = DefaultStyles.linkColor
        }
        link.tooltip(text = htmlNode.attr("href"))
        link.setOnAction { _ ->
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(URI(htmlNode.attr("href")))
            }
        }
        node = link
    } else if (htmlNode is TextNode) {
        // logger.debug { "processing text: ${htmlNode.text()}" }
        node = Text(htmlNode.text())
        node.style {
            fill = Color.WHITE
            textFill = Color.WHITE
        }
    } else if (htmlNode.childNodes().size > 0) {
        val hbox = if (htmlNode is Element && htmlNode.tagName() == "p") TextFlow() else VBox()
        // hbox.maxWidth(30.0)
        hbox.style {
            backgroundColor = multi(DefaultStyles.darkerBackgroundColor)
            //  minWidth = 28.em
        }
        htmlNode.childNodes().forEach { hbox.children.add(parseNode(it)) }
        node = hbox
    } else if (htmlNode is Element && htmlNode.tagName() == "br") {
        //  logger.debug { "processing line break" }
        node = Text(" \n")
    } else {
        logger.warn { "missing case: " + htmlNode.nodeName() }
    }
    return node!!
}