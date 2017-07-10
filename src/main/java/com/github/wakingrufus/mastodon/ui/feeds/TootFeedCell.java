package com.github.wakingrufus.mastodon.ui.feeds;

import com.github.wakingrufus.mastodon.feed.FeedState;
import com.sys1yagi.mastodon4j.api.entity.Status;
import javafx.scene.control.ListCell;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TootFeedCell extends ListCell<FeedState<Status>> {


    public TootFeedCell() {

    }

    @Override
    protected void updateItem(FeedState<Status> item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);


    }
}
