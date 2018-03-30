package com.github.wakingrufus.mastodon.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import mu.KLogging
import java.io.File
import java.io.IOException

class FileConfigurationHandler(val file: File = File(File(System.getProperty("user.home")), ".mastodon.txt"))
    : ConfigurationHandler {
    companion object : KLogging() {
        val objectMapper = ObjectMapper()
                .registerModule(ParameterNamesModule())
                .registerModule(KotlinModule())
    }

    override fun readFileConfig(): ConfigData {
        var configData: ConfigData? = null
        if (!file.exists()) {
            logger.info("config not found, creating")
            try {
                file.createNewFile()
                configData = ConfigData()
            } catch (e: IOException) {
                logger.error("Error creating config file: " + e.localizedMessage, e)
            }

        } else {
            configData = try {
                objectMapper.readValue(file, ConfigData::class.java)
            } catch (e: IOException) {
                logger.error("Error reading config file: " + e.localizedMessage, e)
                ConfigData()
            }
        }
        return configData!!
    }

    override fun saveConfig(configData: ConfigData) {
        objectMapper.writeValue(file, configData)
    }


    override fun addAccountToConfig(configData: ConfigData, identity: AccountConfig): ConfigData =
            configData.copy(identities = configData.identities.plus(identity))
}