package com.github.wakingrufus.mastodon.ui.login;

import com.github.wakingrufus.mastodon.events.OAuthTokenEvent;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OauthController {
    private final MastodonClient client;
    private final AppRegistration appRegistration;
    private final String oauthUrl;
    @FXML
    TextField tokenField;
    @FXML
    Button loginButton;
    @FXML
    WebView webView;

    public OauthController(MastodonClient client, AppRegistration appRegistration, String oAuthUrl) {
        this.oauthUrl = oAuthUrl;
        this.client = client;
        this.appRegistration = appRegistration;
    }

    @FXML
    public void initialize() {
        webView.getEngine().load(oauthUrl);
        loginButton.setOnAction((event) -> {
            //   AccessToken accessToken = clientService.getAccessToken(client, appRegistration.getClientId(), appRegistration.getClientSecret(), tokenField.getText());

            OAuthTokenEvent OAuthTokenEvent = new OAuthTokenEvent(tokenField.getText(),
                    event.getTarget(), appRegistration, tokenField.getText(), client);
            Event.fireEvent(event.getTarget(), OAuthTokenEvent);
        });
    }
}
