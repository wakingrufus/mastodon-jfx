package com.github.wakingrufus.mastodon.ui.feeds;

import com.sys1yagi.mastodon4j.api.entity.Status;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class TootCell extends ListCell<Status> {


    public TootCell() {

    }

    @Override
    protected void updateItem(Status item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
        if (item != null) {
            TootController tootController = new TootController(item);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/toot.fxml"));
            fxmlLoader.setController(tootController);
            try {
                Parent load = fxmlLoader.load();
                setGraphic(load);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
