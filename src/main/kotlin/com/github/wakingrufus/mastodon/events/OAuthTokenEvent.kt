package com.github.wakingrufus.mastodon.events

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import javafx.beans.NamedArg
import javafx.event.Event
import javafx.event.EventTarget
import javafx.event.EventType

val OAUTH_TOKEN: EventType<OAuthTokenEvent> = EventType<OAuthTokenEvent>(Event.ANY, "OAUTH_TOKEN")

class OAuthTokenEvent(@NamedArg("source") source: Any,
                      @NamedArg("target") target: EventTarget,
                      val appRegistration: AppRegistration,
                      val client: MastodonClient,
                      val token: String)
    : Event(source, target, OAUTH_TOKEN) {

}
