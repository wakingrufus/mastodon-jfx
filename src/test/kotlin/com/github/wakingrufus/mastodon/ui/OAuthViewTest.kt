package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.TornadoFxTest
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.waitFor
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import mu.KLogging
import org.junit.Test
import org.testfx.api.FxAssert
import org.testfx.matcher.base.NodeMatchers

class OAuthViewTest : TornadoFxTest() {
    companion object : KLogging()

    @Test
    fun should_contain_button() {
        val onComplete: (AccountState) -> Unit = {}
        val instance = OAuthView(
                register = { AppRegistration() },
                serverClient = { mock {} },
                onComplete = onComplete,
                oAuthUrlBuilder = { "url" },
                completeOAuthFunction = { onComplete })

        showView(instance)
        FxAssert.verifyThat("#instance-field", NodeMatchers.hasText(""))
        // expect:
        FxAssert.verifyThat("#instance-form", NodeMatchers.hasChildren(1, "#instance-field-label"))
        FxAssert.verifyThat("#instance-form", NodeMatchers.hasChildren(1, "#instance-field"))
        FxAssert.verifyThat("#instance-form", NodeMatchers.hasChildren(1, "#instance-form-submit"))
        FxAssert.verifyThat("#oauth-root", NodeMatchers.hasChildren(0, "#instance-login"))
    }

    @Test
    fun showLogin() {
        val onComplete: (AccountState) -> Unit = {}
        val instance = OAuthView(
                register = { AppRegistration() },
                serverClient = { mock {} },
                onComplete = onComplete,
                oAuthUrlBuilder = { "url" },
                completeOAuthFunction = { onComplete })

        showView(instance)
        FxAssert.verifyThat("#instance-field", NodeMatchers.hasText(""))
        clickOn("#instance-field").write("url")
        clickOn("#instance-form-submit")
        waitFor(condition = { false }, maxMillis = 1000)
        // expect:
        FxAssert.verifyThat("#oauth-root", NodeMatchers.hasChildren(1, "#instance-login"))
    }
}