package com.github.wakingrufus.mastodon.account

data class Identity(val username: String, val server: String) {
    companion object {}
}

fun Identity.Companion.fromFqn(fqn: String): Identity {
    return Identity(username = fqn.split("@")[1], server = fqn.split("@")[2])

}
