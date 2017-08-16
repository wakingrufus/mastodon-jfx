package com.github.wakingrufus.mastodon.account

import com.sys1yagi.mastodon4j.MastodonClient


fun GetClientForAccount(id: Long, accountStates: List<AccountState>): MastodonClient? {
    var found: MastodonClient? = null
    val findFirst = accountStates.stream().filter { accountState -> accountState.account.id == id }.findFirst()
    if (findFirst.isPresent) {
        found = findFirst.get().client
    }
    return found
}
