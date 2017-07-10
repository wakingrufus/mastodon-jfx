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
public class OAuthTokenEvent extends Event {
    public static final EventType<OAuthTokenEvent> OAUTH_TOKEN = new EventType<>(Event.ANY, "OAUTH_TOKEN");
    private String token;
    private MastodonClient client;
    private AppRegistration appRegistration;

    public OAuthTokenEvent(@NamedArg("source") Object source,
                           @NamedArg("target") EventTarget target,
                           AppRegistration appRegistration, String token, MastodonClient client) {
        super(source, target, OAUTH_TOKEN);
        this.token = token;
        this.appRegistration = appRegistration;
        this.client = client;
    }

}
