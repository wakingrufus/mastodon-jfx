package com.github.wakingrufus.mastodon.ui.settings;

import com.github.wakingrufus.mastodon.account.AccountState;
import com.github.wakingrufus.mastodon.events.ViewAccountEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SettingsAccountCell extends ListCell<AccountState> {


    public SettingsAccountCell() {

    }

    @Override
    protected void updateItem(AccountState item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
        SettingsAccountController settingsAccountController = new SettingsAccountController(item);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/settings-account.fxml"));
        fxmlLoader.setController(settingsAccountController);
        try {
            Parent load = fxmlLoader.load();
            setGraphic(load);
        } catch (IOException e) {
            log.error("error loading account pane", e);
        }
        if (item != null) {
            this.addEventHandler(ActionEvent.ACTION,
                    viewAccountEvent ->
                            Event.fireEvent(viewAccountEvent.getTarget(),
                                    new ViewAccountEvent(item, viewAccountEvent.getTarget(),
                                            ViewAccountEvent.VIEW_ACCOUNT)));
        }
    }
}
