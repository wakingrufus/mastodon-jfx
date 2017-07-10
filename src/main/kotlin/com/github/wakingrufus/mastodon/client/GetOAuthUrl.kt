package com.github.wakingrufus.mastodon.client

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.Scope
import com.sys1yagi.mastodon4j.api.method.Apps
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun getOAuthUrl(client: MastodonClient, clientId: String, redirectUri: String): String {
    val apps = Apps(client)
    return apps.getOAuthUrl(clientId, Scope(Scope.Name.ALL), redirectUri)
}