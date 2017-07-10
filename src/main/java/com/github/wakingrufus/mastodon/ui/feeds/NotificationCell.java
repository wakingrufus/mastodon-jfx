package com.github.wakingrufus.mastodon.ui.feeds;

import com.sys1yagi.mastodon4j.api.entity.Notification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class NotificationCell extends ListCell<Notification> {


    public NotificationCell() {

    }

    @Override
    protected void updateItem(Notification item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
        log.info("updating item");
        if (item != null) {
            NotificationController controller = new NotificationController(item);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/notification.fxml"));
            fxmlLoader.setController(controller);
            try {
                Parent load = fxmlLoader.load();
                setGraphic(load);
            } catch (IOException e) {
                log.error("error loading notification pane", e);
            }

        }

    }
}
