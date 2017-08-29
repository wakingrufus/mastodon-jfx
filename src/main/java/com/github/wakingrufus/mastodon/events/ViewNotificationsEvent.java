package com.github.wakingrufus.mastodon.events;

import com.github.wakingrufus.mastodon.data.AccountState;
import com.sys1yagi.mastodon4j.api.entity.Notification;
import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ViewNotificationsEvent extends Event {
    public static final EventType<ViewNotificationsEvent> VIEW_NOTIFICATIONS = new EventType<>(Event.ANY, "VIEW_NOTIFICATIONS");
    private final AccountState account;
    private final ObservableList<Notification> feed;

    public ViewNotificationsEvent(@NamedArg("source") Object source,
                                  @NamedArg("target") EventTarget target,
                                  AccountState accountState,
                                  ObservableList<Notification> feed) {
        super(source, target, VIEW_NOTIFICATIONS);
        this.account = accountState;
        this.feed = feed;
    }

    public AccountState getAccount() {
        return account;
    }

    public ObservableList<Notification> getFeed() {
        return feed;
    }
}
