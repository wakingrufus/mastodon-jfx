package com.github.wakingrufus.mastodon.events;

import com.github.wakingrufus.mastodon.account.AccountState;
import com.sys1yagi.mastodon4j.api.entity.Status;
import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ViewFeedEvent extends Event {
    public static final EventType<ViewFeedEvent> VIEW_FEED = new EventType<>(Event.ANY, "VIEW_FEED");
    private final AccountState account;
    private final ObservableList<Status> feed;

    public ViewFeedEvent(@NamedArg("source") Object source,
                         @NamedArg("target") EventTarget target,
                         AccountState accountState,
                         ObservableList<Status> feed) {
        super(source, target, VIEW_FEED);
        this.account = accountState;
        this.feed = feed;
    }

    public AccountState getAccount() {
        return account;
    }

    public ObservableList<Status> getFeed() {
        return feed;
    }
}
