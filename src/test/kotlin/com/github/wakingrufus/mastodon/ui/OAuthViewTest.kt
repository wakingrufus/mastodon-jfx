package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.TornadoFxTest
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.OAuthModel
import com.github.wakingrufus.mastodon.waitFor
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import mu.KLogging
import org.junit.Test
import org.testfx.api.FxAssert
import org.testfx.matcher.base.NodeMatchers
import kotlin.test.assertEquals

class OAuthViewTest : TornadoFxTest() {
    companion object : KLogging()

    @Test
    fun render() {
        val onComplete: (AccountState) -> Unit = {}
        showViewWithParams<OAuthView>(mapOf(
                "buildModel" to { OAuthModel(appRegistration = AppRegistration(), client = mock {}) },
                "onComplete" to onComplete,
                "oAuthUrlBuilder" to { "url" },
                "completeOAuthFunction" to { onComplete }))

        FxAssert.verifyThat("#instance-field", NodeMatchers.hasText(""))
        // expect:
        FxAssert.verifyThat("#instance-form", NodeMatchers.hasChildren(1, "#instance-field-label"))
        FxAssert.verifyThat("#instance-form", NodeMatchers.hasChildren(1, "#instance-field"))
        FxAssert.verifyThat("#instance-form", NodeMatchers.hasChildren(1, "#instance-form-submit"))
        FxAssert.verifyThat("#oauth-root", NodeMatchers.hasChildren(1, "#login-wrapper"))
        FxAssert.verifyThat("#login-wrapper", NodeMatchers.hasChildren(0, "#instance-login"))

    }

    @Test
    fun showLogin() {
        val onComplete: (AccountState) -> Unit = {}
        showViewWithParams<OAuthView>(mapOf(
                "buildModel" to { url: String ->
                    assertEquals("url", url)
                    OAuthModel(appRegistration = AppRegistration(), client = mock {})
                },
                "onComplete" to onComplete,
                "oAuthUrlBuilder" to { "url" },
                "completeOAuthFunction" to { onComplete }))

        FxAssert.verifyThat("#login-wrapper", NodeMatchers.hasChildren(0, "#instance-login"))
        FxAssert.verifyThat("#instance-field", NodeMatchers.hasText(""))
        clickOn("#instance-field").write("url")
        clickOn("#instance-form-submit")
        waitFor(condition = { false }, maxMillis = 2000)
        // expect:
        FxAssert.verifyThat("#login-wrapper", NodeMatchers.hasChildren(1, "#instance-login"))
        FxAssert.verifyThat("#login-wrapper", NodeMatchers.hasChildren(1, "#token-form"))
    }
}