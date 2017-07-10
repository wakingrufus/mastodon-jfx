package com.github.wakingrufus.mastodon.events;

import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class OAuthStartEvent extends Event {
    public static final EventType<OAuthStartEvent> OAUTH_START = new EventType<>(Event.ANY, "OAUTH_START");


    private AppRegistration appRegistration;
    MastodonClient client;

    public OAuthStartEvent(@NamedArg("source") Object source,
                           @NamedArg("target") EventTarget target,
                           AppRegistration appRegistration,  MastodonClient client) {
        super(source, target, OAUTH_START);
        this.client = client;
        this.appRegistration = appRegistration;
    }

}
