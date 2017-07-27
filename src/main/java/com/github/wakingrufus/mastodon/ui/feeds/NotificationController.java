package com.github.wakingrufus.mastodon.ui.feeds;

import com.github.wakingrufus.mastodon.ui.ViewAccountKt;
import com.sys1yagi.mastodon4j.api.entity.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationController {
    private final Notification notification;
    @FXML
    Label displayName;
    @FXML
    Label notificationTime;
    @FXML
    Label message;
    @FXML
    HBox accountView;
    @FXML
    HBox tootView;

    public NotificationController(Notification notification) {
        this.notification = notification;
    }

    @FXML
    public void initialize() {
        displayName.setText(notification.getAccount().getDisplayName());
        if (notification.getType().equalsIgnoreCase("follow")) {
            message.setText("followed you.");
            ViewAccountKt.viewAccount(accountView, notification.getAccount());
        } else if (notification.getType().equalsIgnoreCase("favourite")) {
            message.setText("has favourited your status: " + notification.getStatus().getContent());

        } else {
            message.setText(notification.getType());
        }
        notificationTime.setText(notification.getCreatedAt());
    }
}
