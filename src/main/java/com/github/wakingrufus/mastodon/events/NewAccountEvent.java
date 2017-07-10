package com.github.wakingrufus.mastodon.events;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewAccountEvent extends Event {
    public static final EventType<NewAccountEvent> NEW_ACCOUNT = new EventType<>(Event.ANY, "NEW_ACCOUNT");
    private final String accessToken;
    private final String clientId;
    private final String clientSecret;
    private final String server;

    public NewAccountEvent(@NamedArg("source") Object source,
                           @NamedArg("target") EventTarget target,
                           String accessToken,
                           String clientId,
                           String clientSecret,
                           String server) {
        super(source, target, NEW_ACCOUNT);
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.server = server;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getServer() {
        return server;
    }
}
