package com.github.wakingrufus.mastodon.config

import com.github.wakingrufus.mastodon.account.AccountConfig
import java.util.*

data class ConfigData(val identities: Set<AccountConfig> = HashSet<AccountConfig>()) {
}