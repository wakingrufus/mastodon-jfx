package com.github.wakingrufus.mastodon.account

import com.github.wakingrufus.mastodon.account.AccountConfig
import com.github.wakingrufus.mastodon.config.Config

fun addAccountToConfig(config: com.github.wakingrufus.mastodon.config.Config, account: com.github.wakingrufus.mastodon.account.AccountConfig): com.github.wakingrufus.mastodon.config.Config {
    config.addAccount(account)
    return config
}