package com.github.wakingrufus.mastodon.config

import java.util.*

data class ConfigData(val identities: Set<AccountConfig> = HashSet()) {
}