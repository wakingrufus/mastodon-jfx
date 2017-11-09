package com.github.wakingrufus.mastodon.client

import java.net.URL

fun parseUrl(url: String) : String {
    try {
        val urlObject = URL(url)
        return urlObject.host
    } catch (e: Exception) {
        return url
    }
}