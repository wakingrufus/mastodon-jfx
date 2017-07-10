package com.github.wakingrufus.mastodon.ui.feeds;

import com.github.wakingrufus.mastodon.feed.FeedState;
import com.github.wakingrufus.mastodon.feed.FeedType;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FeedCell extends ListCell<FeedState> {


    public FeedCell() {
    }

    @Override
    protected void updateItem(FeedState item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
        log.info("updating item");
        if (item != null) {
            if (item.getFeedType() == FeedType.NOTIFICATION) {
                NotificationFeedController controller = new NotificationFeedController(item);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/notification-feed.fxml"));
                fxmlLoader.setController(controller);
                try {
                    setGraphic(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (item.getFeedType() == FeedType.TOOT) {
                TootFeedController tootController = new TootFeedController(item);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/toot-feed.fxml"));
                fxmlLoader.setController(tootController);
                try {
                    setGraphic(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                log.warn("invalid feedtype");
            }
        }
    }
}
