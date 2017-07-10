package com.github.wakingrufus.mastodon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wakingrufus.mastodon.account.AccountConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileConfig implements Config {
    private final File file;
    private final ObjectMapper objectMapper;

    public FileConfig(File file, ObjectMapper objectMapper) {
        this.file = file;
        this.objectMapper = objectMapper;
    }


    @Override
    public ConfigData getConfig() {

        ConfigData configData = null;
        if (!file.exists()) {
            log.info("config not found, creating");
            try {
                boolean created = file.createNewFile();
                configData = new ConfigData();
            } catch (IOException e) {
                log.error("Error creating config file: " + e.getLocalizedMessage(), e);
            }
        } else {
            try {
                configData = objectMapper.readValue(file, ConfigData.class);
            } catch (IOException e) {
                log.error("Error reading config file: " + e.getLocalizedMessage(), e);
                configData = new ConfigData();
            }
        }
        return configData;
    }

    public void addAccount(AccountConfig identity) {
        ConfigData configData = getConfig();
        configData.getIdentities().add(identity);
        try {
            objectMapper.writeValue(file, configData);
        } catch (IOException e) {
            log.error("Error writing config file: " + e.getLocalizedMessage(), e);
        }
    }

}
