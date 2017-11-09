package com.github.wakingrufus.mastodon.ui

import com.github.wakingrufus.mastodon.data.AccountState
import com.sys1yagi.mastodon4j.api.entity.Account
import javafx.collections.ObservableList
import mu.KLogging
import tornadofx.*

class AccountChooserView(accountFragment: (String, Account) -> AccountFragment =
                         { s: String, a: Account -> AccountFragment(server = s, account = a) })
    : View() {
    companion object : KLogging()

    val accounts: ObservableList<AccountState> by param()
    var choice: AccountState? = null

    override val root = vbox {
        id = "account-choices"
        children.bind(accounts) {
            button {
                id = "account-choice-" + it.account.userName + "@" + it.client.getInstanceName()
                graphic = accountFragment(it.client.getInstanceName(), it.account).root
                action {
                    choice = it
                    close()
                }
            }
        }
    }

    fun getAccount(): AccountState? = choice

}