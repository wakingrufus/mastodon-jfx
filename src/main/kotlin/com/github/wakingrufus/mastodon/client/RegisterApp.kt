package com.github.wakingrufus.mastodon.client

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.Scope
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import com.sys1yagi.mastodon4j.api.method.Apps
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun registerApp(mastodonClient: MastodonClient): AppRegistration? {
    val apps = Apps(mastodonClient)
    try {
        val appRegistration = apps.createApp(
                "mastodon-jfx",
                "urn:ietf:wg:oauth:2.0:oob",
                Scope(Scope.Name.ALL),
                "https://github.com/wakingrufus").execute()
        return appRegistration

    } catch (e: Mastodon4jRequestException) {
        logger.error("error", e)
    }
    return null
}