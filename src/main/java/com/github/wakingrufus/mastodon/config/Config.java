package com.github.wakingrufus.mastodon.config;

import com.github.wakingrufus.mastodon.account.AccountConfig;


public interface Config {
    ConfigData getConfig();

    void addAccount(AccountConfig account);
}
