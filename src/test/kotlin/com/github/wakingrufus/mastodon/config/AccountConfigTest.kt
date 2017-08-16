package com.github.wakingrufus.mastodon.config

import com.github.wakingrufus.mastodon.account.AccountConfig
import mu.KLogging
import org.junit.Assert.assertEquals
import org.junit.Test


class AccountConfigTest {
    companion object : KLogging()

    @Test
    fun testDataClass() {
        val config1 = AccountConfig(accessToken = "accessToken", clientId = "clientId", clientSecret = "clientSecret", server = "server", username = "username")
        val config2 = config1.copy()
        assertEquals("objects are equal", config1, config2)
        assertEquals("hashcode is equal", config1.hashCode(), config2.hashCode())
        assertEquals("toString is equal", config1.toString(), config2.toString())

    }
}