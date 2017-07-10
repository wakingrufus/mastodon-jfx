package com.github.wakingrufus.mastodon.events;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerConnectEvent extends Event {
    public static final EventType<ServerConnectEvent> SERVER_CONNECT = new EventType<>(Event.ANY, "SERVER_CONNECT");
    private final String server;

    public ServerConnectEvent(@NamedArg("source") Object source,
                              @NamedArg("target") EventTarget target,
                              String server) {
        super(source, target, SERVER_CONNECT);
        this.server = server;
    }

    public String getServer() {
        return server;
    }
}
