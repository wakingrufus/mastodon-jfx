package com.github.wakingrufus.mastodon.ui.settings;


import com.github.wakingrufus.mastodon.account.AccountState;
import com.github.wakingrufus.mastodon.events.CreateAccountEvent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SettingsController {
    private final ObservableList<AccountState> accountStates;
    @FXML
    Button newIdButton;
    @FXML
    ListView<AccountState> listView;

    public SettingsController(ObservableList<AccountState> accounts) {
        this.accountStates = accounts;
    }

    @FXML
    public void handleNewIdButtonAction(ActionEvent event) {
        Event.fireEvent(newIdButton, new CreateAccountEvent());
    }

    public void setAccounts(List<AccountState> accounts) {
        listView.getItems().addAll(accounts);
    }

    public void addAccounts(AccountState... accounts) {
        listView.getItems().addAll(accounts);
    }

    @FXML
    public void initialize() {
        listView.setItems(accountStates);
        listView.setCellFactory(listView1 -> new SettingsAccountCell());
    }
}
