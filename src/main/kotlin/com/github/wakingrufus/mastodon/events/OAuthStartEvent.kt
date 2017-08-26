package com.github.wakingrufus.mastodon.events

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration
import javafx.beans.NamedArg
import javafx.event.Event
import javafx.event.EventTarget
import javafx.event.EventType

val OAUTH_START: EventType<OAuthStartEvent> = EventType<OAuthStartEvent>(Event.ANY, "OAUTH_START")

class OAuthStartEvent(@NamedArg("source") source: Any,
                      @NamedArg("target") target: EventTarget,
                      val appRegistration: AppRegistration,
                      val client: MastodonClient)
    : Event(source, target, OAUTH_START) {

}
