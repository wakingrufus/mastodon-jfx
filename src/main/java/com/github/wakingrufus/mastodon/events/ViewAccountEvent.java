package com.github.wakingrufus.mastodon.events;

import com.github.wakingrufus.mastodon.account.AccountState;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ViewAccountEvent extends Event {
    public static final EventType<ViewAccountEvent> VIEW_ACCOUNT = new EventType<>(Event.ANY, "VIEW_ACCOUNT");
    private final AccountState account;

    public ViewAccountEvent(@NamedArg("source") AccountState source, @NamedArg("target") EventTarget target,
                            @NamedArg("eventType") EventType<ViewAccountEvent> eventType) {
        super(source, target, eventType);
        this.account = source;
    }

    public AccountState getAccount() {
        return account;
    }
}
