package com.github.wakingrufus.mastodon.account

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.Account

data class AccountState(val account: Account, val client: MastodonClient) {
}