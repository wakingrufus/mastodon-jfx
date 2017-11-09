package com.github.wakingrufus.mastodon.data

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration

data class OAuthModel (val client: MastodonClient, val appRegistration: AppRegistration, val token: String? = null){
}