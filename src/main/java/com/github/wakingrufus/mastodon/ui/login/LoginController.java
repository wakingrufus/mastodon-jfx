package com.github.wakingrufus.mastodon.ui.login;

import com.github.wakingrufus.mastodon.events.ServerConnectEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginController {
    @FXML
    TextField serverField;

    @FXML
    Button tokenButton;

    @FXML
    public void initialize() {
        tokenButton.setOnAction(e -> {
            log.info("server: " + serverField.getText());
            Event.fireEvent(e.getTarget(), new ServerConnectEvent(e.getSource(), e.getTarget(), serverField.getText()));
        });
    }
}
