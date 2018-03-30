package com.github.wakingrufus.mastodon.config

data class AccountConfig(val accessToken: String,
                         val clientId: String,
                         val clientSecret: String,
                         val username: String,
                         val server: String) {
}