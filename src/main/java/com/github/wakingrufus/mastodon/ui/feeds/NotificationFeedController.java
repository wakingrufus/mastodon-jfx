package com.github.wakingrufus.mastodon.ui.feeds;

import com.github.wakingrufus.mastodon.feed.FeedState;
import com.sys1yagi.mastodon4j.api.entity.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationFeedController {
    private final FeedState<Notification> feedState;
    @FXML
    ListView<Notification> notifications;

    public NotificationFeedController(FeedState<Notification> feedState) {
        super();
        notifications.setItems(feedState.getItems());
        this.feedState = feedState;
    }

    public void initialize() {
        notifications.setCellFactory(status -> new NotificationCell());
    }
}
