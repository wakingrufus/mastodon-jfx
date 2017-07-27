package com.github.wakingrufus.mastodon.ui.feeds;

import com.github.wakingrufus.mastodon.ui.FittedWebView;
import com.github.wakingrufus.mastodon.ui.ViewAccountKt;
import com.sys1yagi.mastodon4j.api.entity.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TootController {
    private final Status status;
    @FXML
    HBox accountView;
    @FXML
    FittedWebView content;

    public TootController(Status status) {
        this.status = status;
    }

    @FXML
    public void initialize() {
        ViewAccountKt.viewAccount(accountView,status.getAccount());
        content.setContent(status.getContent());
        content.setHtmlStylesheetLocation(getClass().getResource("/css/toot-content.css").toString());
    }


}
