package com.github.wakingrufus.mastodon.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.wakingrufus.mastodon.account.AccountConfig
import mu.KLogging
import java.io.File
import java.io.IOException


class FileConfig(private val file: File, private val objectMapper: ObjectMapper) : Config {
    companion object : KLogging()


    override val config: ConfigData
        get() {

            var configData: ConfigData? = null
            if (!file.exists()) {
                logger.info("config not found, creating")
                try {
                    val created = file.createNewFile()
                    configData = ConfigData()
                } catch (e: IOException) {
                    logger.error("Error creating config file: " + e.localizedMessage, e)
                }

            } else {
                try {
                    configData = objectMapper.readValue(file, ConfigData::class.java)
                } catch (e: IOException) {
                    logger.error("Error reading config file: " + e.localizedMessage, e)
                    configData = ConfigData()
                }
            }
            return configData!!
        }

    override fun addAccount(identity: AccountConfig) {
        try {
            objectMapper.writeValue(file, config.copy(identities = config.identities.plus(identity)))
        } catch (e: IOException) {
            logger.error("Error writing config file: " + e.localizedMessage, e)
        }

    }

}
