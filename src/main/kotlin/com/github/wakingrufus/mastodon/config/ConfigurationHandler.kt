package com.github.wakingrufus.mastodon.config

import com.github.wakingrufus.mastodon.account.AccountConfig


interface ConfigurationHandler {

    fun readFileConfig(): ConfigData
    fun saveConfig(configData: ConfigData)
    fun addAccountToConfig(configData: ConfigData, identity: AccountConfig): ConfigData
}