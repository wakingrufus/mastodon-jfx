package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.data.AccountState
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.collections.FXCollections
import javafx.stage.Stage
import mu.KLogging
import org.junit.Test
import org.testfx.api.FxAssert.verifyThat
import org.testfx.framework.junit.ApplicationTest
import org.testfx.matcher.base.NodeMatchers
import tornadofx.App
import tornadofx.View
import tornadofx.plusAssign
import tornadofx.stackpane


class AccountChooserViewTest : ApplicationTest() {
    companion object : KLogging()
    override fun start(stage: Stage) {
        val app = App(TestView::class)
        app.start(stage)
    }

    class TestView : View() {
        override val root = stackpane {
            val account: Account = mock {
                on { displayName } doReturn "displayName"
                on { url } doReturn ""
                on { userName } doReturn "userName"
            }
            val accountState = AccountState(
                    account = account,
                    client = mock { on { getInstanceName() } doReturn "instanceName" })
            val accounts = FXCollections.observableArrayList<AccountState>()
            accounts.add(accountState)
            this += find<AccountChooserView>(mapOf("accounts" to accounts)).root
        }
    }

    @Test
    fun should_contain_button() {
        // expect:
        verifyThat("#account-choices", NodeMatchers.hasChildren(1, "#account-choice-userName@instanceName"))
    }
}