package com.github.wakingrufus.mastodon.ui.feeds;

import com.github.wakingrufus.mastodon.feed.FeedState;
import com.sys1yagi.mastodon4j.api.entity.Status;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TootFeedController {
    private final FeedState<Status> feedState;
    @FXML
    private ListView<Status> toots;

    public TootFeedController(FeedState<Status> feedState) {
        super();
        this.feedState = feedState;
    }

    public void initialize() {
        toots.setItems(feedState.getItems());
        toots.setCellFactory(status -> new TootCell());
    }
}
