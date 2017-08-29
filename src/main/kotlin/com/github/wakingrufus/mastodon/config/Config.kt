package com.github.wakingrufus.mastodon.config

import com.github.wakingrufus.mastodon.account.AccountConfig

interface Config {
    val config: ConfigData

    fun addAccount(account: AccountConfig)
}
