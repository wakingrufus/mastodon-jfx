package com.github.wakingrufus.mastodon.events

import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.StatusFeed
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.beans.NamedArg
import javafx.collections.ObservableList
import javafx.event.Event
import javafx.event.EventTarget
import javafx.event.EventType

val VIEW_FEED: EventType<ViewFeedEvent> = EventType<ViewFeedEvent>(Event.ANY, "VIEW_FEED")

class ViewFeedEvent(@NamedArg("source") source: Any,
                    @NamedArg("target") target: EventTarget,
                    val accountState: AccountState,
                    val feed: StatusFeed)
    : Event(source, target, VIEW_FEED) {

}