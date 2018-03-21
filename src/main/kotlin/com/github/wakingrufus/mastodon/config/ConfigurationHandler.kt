package com.github.wakingrufus.mastodon.config

interface ConfigurationHandler {

    fun readFileConfig(): ConfigData
    fun saveConfig(configData: ConfigData)
    fun addAccountToConfig(configData: ConfigData, identity: AccountConfig): ConfigData
}