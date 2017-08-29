package com.github.wakingrufus.mastodon.events;

import com.github.wakingrufus.mastodon.data.AccountState;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoostStatusEvent extends Event {
    public static final EventType<BoostStatusEvent> BOOST_STATUS = new EventType<>(Event.ANY, "BOOST_STATUS");
    private final AccountState account;
    private final Long statusId;

    public BoostStatusEvent(@NamedArg("source") Object source,
                            @NamedArg("target") EventTarget target,
                            AccountState accountState,
                            Long statusId) {
        super(source, target, BOOST_STATUS);
        this.account = accountState;
        this.statusId = statusId;
    }

    public AccountState getAccount() {
        return account;
    }

    public Long getStatusId() {
        return statusId;
    }

}
