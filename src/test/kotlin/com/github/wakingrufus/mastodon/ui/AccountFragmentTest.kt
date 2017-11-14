package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.TornadoFxTest
import com.sys1yagi.mastodon4j.api.entity.Account
import mu.KLogging
import org.junit.Test
import org.testfx.api.FxAssert
import org.testfx.matcher.base.NodeMatchers


class AccountFragmentTest : TornadoFxTest() {
    companion object : KLogging()

    @Test
    fun test() {
        showViewWithParams<AccountFragment>(mapOf(
                "server" to "server",
                "account" to Account(
                        id = 1,
                        displayName = "displayName",
                        userName = "username")))

        FxAssert.verifyThat("#display-name", NodeMatchers.hasText("displayName"))
    }
}