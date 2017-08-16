package com.github.wakingrufus.mastodon.account

import com.github.wakingrufus.mastodon.config.Config

fun addAccountToConfig(config: Config, account: AccountConfig): Config {
    config.addAccount(account)
    return config
}