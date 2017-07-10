package com.github.wakingrufus.mastodon.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateAccountEvent extends Event {
    public static final EventType<CreateAccountEvent> CREATE_ACCOUNT = new EventType<>(Event.ANY, "CREATE_ACCOUNT");

    public CreateAccountEvent() {
        super(null, null, CREATE_ACCOUNT);
    }
}
