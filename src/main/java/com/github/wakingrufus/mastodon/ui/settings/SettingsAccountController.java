package com.github.wakingrufus.mastodon.ui.settings;


import com.github.wakingrufus.mastodon.account.AccountState;
import com.github.wakingrufus.mastodon.events.ViewFeedEvent;
import com.github.wakingrufus.mastodon.events.ViewNotificationsEvent;
import com.github.wakingrufus.mastodon.ui.ViewAccountKt;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SettingsAccountController {
    private final AccountState accountState;
    @FXML
    private Button homeFeedButton;
    @FXML
    private Button publicFeedButton;
    @FXML
    private Button federatedFeedButton;
    @FXML
    private Button notificationFeedButton;
    @FXML
    private HBox accountView;
    @FXML
    private Label serverName;

    public SettingsAccountController(AccountState accountState) {
        this.accountState = accountState;
    }

    @FXML
    public void initialize() {
        if (accountState != null) {
            serverName.setText(accountState.getClient().getInstanceName());
            ViewAccountKt.viewAccount(accountView, accountState.getAccount());
            homeFeedButton.setOnAction(actionEvent -> Event.fireEvent(actionEvent.getTarget(),
                    new ViewFeedEvent(actionEvent.getSource(), actionEvent.getTarget(), accountState, accountState.getHomeFeed())));
            publicFeedButton.setOnAction(actionEvent -> Event.fireEvent(actionEvent.getTarget(),
                    new ViewFeedEvent(actionEvent.getSource(), actionEvent.getTarget(), accountState, accountState.getPublicFeed())));
            federatedFeedButton.setOnAction(actionEvent -> Event.fireEvent(actionEvent.getTarget(),
                    new ViewFeedEvent(actionEvent.getSource(), actionEvent.getTarget(), accountState, accountState.getFederatedFeed())));
            notificationFeedButton.setOnAction(actionEvent ->
                    Event.fireEvent(actionEvent.getTarget(),
                            new ViewNotificationsEvent(actionEvent.getSource(),
                                    actionEvent.getTarget(),
                                    accountState,
                                    accountState.getNotificationFeed())));

        }
    }


}
