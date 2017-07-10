package com.github.wakingrufus.mastodon.ui.settings;


import com.github.wakingrufus.mastodon.account.AccountState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SettingsAccountController {
    private final AccountState accountState;
    @FXML
    private Button accountButton;
    @FXML
    private Label accountName;
    @FXML
    private Label serverName;
    @FXML
    private Image avatar;
    @FXML
    private ImageView avatarView;

    public SettingsAccountController(AccountState accountState) {
        this.accountState = accountState;
    }

    @FXML
    public void initialize() {
        if (accountState != null) {
            accountName.setText(accountState.getAccount().getDisplayName());
            StringBuilder sb = new StringBuilder();
            sb = sb.append("@");
            sb = sb.append(accountState.getClient().getInstanceName());

            serverName.setText(sb.toString());

            if (!accountState.getAccount().getAvatar().isEmpty()) {
                log.info("avatar: " + accountState.getAccount().getAvatar());
                avatarView.setImage(new Image(accountState.getAccount().getAvatar()));
                accountButton.setGraphic(avatarView);
            }
            accountButton.setOnAction(actionEvent -> log.info(actionEvent.toString()));
        }
    }


}
