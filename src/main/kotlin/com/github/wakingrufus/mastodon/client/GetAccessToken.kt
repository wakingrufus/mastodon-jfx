package com.github.wakingrufus.mastodon.client

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AccessToken
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException
import com.sys1yagi.mastodon4j.api.method.Apps
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun getAccessToken(mastodonClient: MastodonClient, clientId: String, clientSecret: String, authCode: String): AccessToken {
    val apps = Apps(mastodonClient)
    try {
        return apps.getAccessToken(clientId = clientId, clientSecret = clientSecret, code = authCode).execute()
    } catch (e: Mastodon4jRequestException) {
        logger.error("error getting access token: " + e.localizedMessage, e)
        throw e
    }
}