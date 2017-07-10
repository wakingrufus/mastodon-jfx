package com.github.wakingrufus.mastodon.ui.feeds;

import com.sys1yagi.mastodon4j.api.entity.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationController {
    private final Notification notification;
    @FXML
    Label displayName;
    @FXML
    Label type;

    public NotificationController(Notification notification) {
        this.notification = notification;
    }

    @FXML
    public void initialize() {
        displayName.setText(notification.getAccount().getDisplayName());
        type.setText(notification.getType());
    }


}
