package com.github.wakingrufus.mastodon.ui.feeds;

import com.sys1yagi.mastodon4j.api.entity.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TootController {
    private final Status status;
    @FXML
    Label displayName;
    @FXML
    Label content;

    public TootController(Status status) {
        this.status = status;
    }

    @FXML
    public void initialize() {
        if (status.getAccount() != null) {
            displayName.setText(status.getAccount().getDisplayName());
        }
        content.setText(status.getContent());
    }


}
