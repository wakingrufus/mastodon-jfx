package com.github.wakingrufus.mastodon.ui;

import com.github.wakingrufus.mastodon.account.AccountConfig;
import com.github.wakingrufus.mastodon.account.AccountState;
import com.github.wakingrufus.mastodon.account.AddAccountToConfigKt;
import com.github.wakingrufus.mastodon.account.CreateAccountConfigKt;
import com.github.wakingrufus.mastodon.account.CreateAccountStateKt;
import com.github.wakingrufus.mastodon.client.ClientBuilder;
import com.github.wakingrufus.mastodon.client.GetAccessTokenKt;
import com.github.wakingrufus.mastodon.client.GetOAuthUrlKt;
import com.github.wakingrufus.mastodon.client.RegisterAppKt;
import com.github.wakingrufus.mastodon.config.Config;
import com.github.wakingrufus.mastodon.events.CreateAccountEvent;
import com.github.wakingrufus.mastodon.events.NewAccountEvent;
import com.github.wakingrufus.mastodon.events.OAuthStartEvent;
import com.github.wakingrufus.mastodon.events.OAuthTokenEvent;
import com.github.wakingrufus.mastodon.events.ServerConnectEvent;
import com.github.wakingrufus.mastodon.events.ViewFeedEvent;
import com.github.wakingrufus.mastodon.events.ViewNotificationsEvent;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.entity.Status;
import com.sys1yagi.mastodon4j.api.entity.auth.AccessToken;
import com.sys1yagi.mastodon4j.api.entity.auth.AppRegistration;
import com.sys1yagi.mastodon4j.api.method.Accounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UiService {
    private final Stage stage;
    //  private final BorderPane root = new BorderPane();
    private final Config config;
    private Pane conversationBox;
    private ClientBuilder clientBuilder;


    public UiService(Stage stage, ClientBuilder clientBuilder, Config config) {
        this.stage = stage;
        this.clientBuilder = clientBuilder;
        this.config = config;
    }

    public void init() {

        final ObservableList<AccountState> accountList = FXCollections.observableArrayList();
        final ObservableList<ObservableList<Status>> feeds = FXCollections.observableArrayList();

        final double rootEm = Math.rint(new Text().getLayoutBounds().getHeight());

        Pane settingsPane = new StackPane();
        ViewSettingsKt.viewSettings(settingsPane, accountList);

        conversationBox = new StackPane();
        Pane notificationBox = new StackPane();
        notificationBox.setMinWidth(rootEm * 10);
        FXMLLoader fxmlLoader = new FXMLLoader(UiService.class.getResource("/main.fxml"));
        BorderPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("error loading main", e);
        }
        //    root.setStyle("-fx-min-height: 100%;");
        root.setCenter(conversationBox);
        root.setLeft(settingsPane);
        root.setRight(notificationBox);


        conversationBox.setMinHeight(rootEm * 60);
        Scene scene = new Scene(root, rootEm * 80, rootEm * 60);

        // Set the application icon.
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/avatar-default.png")));
        stage.setTitle("mastodon-jfx");
        stage.setScene(scene);
        stage.show();

        ViewAccountFeedsKt.viewAccountFeeds(conversationBox, feeds);

        config.getConfig().getIdentities().forEach(
                identityAuth -> {
                    MastodonClient client = clientBuilder.createAccountClient(identityAuth.getServer(), identityAuth.getAccessToken());
                    accountList.add(CreateAccountStateKt.createAccountState(client));
                });

        root.addEventHandler(ViewFeedEvent.VIEW_FEED, viewFeedEvent -> feeds.add(viewFeedEvent.getFeed()));
        root.addEventHandler(ViewNotificationsEvent.VIEW_NOTIFICATIONS,
                event -> ViewAccountNotificationsKt.viewAccountNotifications(notificationBox, event.getFeed()));

        root.addEventHandler(NewAccountEvent.NEW_ACCOUNT,
                newAccountEvent -> {
                    MastodonClient client = clientBuilder.createAccountClient(newAccountEvent.getServer(), newAccountEvent.getAccessToken());
                    accountList.add(CreateAccountStateKt.createAccountState(client));
                    AccountConfig accountConfig = CreateAccountConfigKt.createAccountConfig(
                            new Accounts(client),
                            newAccountEvent.getAccessToken(),
                            newAccountEvent.getClientId(),
                            newAccountEvent.getClientSecret(),
                            newAccountEvent.getServer());
                    AddAccountToConfigKt.addAccountToConfig(config, accountConfig);
                });
        // CREATE_ACCOUNT -> get server name -> SERVER_CONNECT -> OAUTH_START -> got to oauth page and get token -> OAUTH_TOKEN
        root.addEventHandler(CreateAccountEvent.CREATE_ACCOUNT,
                createAccountEvent -> ViewLoginFormKt.viewLoginForm(conversationBox));
        root.addEventHandler(ServerConnectEvent.SERVER_CONNECT,
                serverConnectEvent -> {
                    MastodonClient client = clientBuilder.createServerClient(serverConnectEvent.getServer());
                    AppRegistration appRegistration = RegisterAppKt.registerApp(client);
                    Event.fireEvent(serverConnectEvent.getTarget(),
                            new OAuthStartEvent(serverConnectEvent.getSource(),
                                    serverConnectEvent.getTarget(),
                                    appRegistration, client));

                });
        root.addEventHandler(OAuthStartEvent.OAUTH_START,
                createAccountEvent -> {
                    AppRegistration appRegistration = createAccountEvent.getAppRegistration();
                    String oAuthUrl = GetOAuthUrlKt.getOAuthUrl(createAccountEvent.getClient(), appRegistration.getClientId(), appRegistration.getRedirectUri());
                    ViewOAuthFormKt.viewOAuthForm(conversationBox, createAccountEvent.getClient(), createAccountEvent.getAppRegistration(), oAuthUrl);
                });

        root.addEventHandler(OAuthTokenEvent.OAUTH_TOKEN,
                oAuthTokenEvent -> {
                    AppRegistration appRegistration = oAuthTokenEvent.getAppRegistration();
                    MastodonClient oldClient = oAuthTokenEvent.getClient();
                    AccessToken accessToken = GetAccessTokenKt.getAccessToken(oAuthTokenEvent.getClient(),
                            oAuthTokenEvent.getAppRegistration().getClientId(),
                            oAuthTokenEvent.getAppRegistration().getClientSecret(),
                            oAuthTokenEvent.getToken());
                    log.info("base url: " + oldClient.getInstanceName());
                    MastodonClient client = clientBuilder.createAccountClient(oldClient.getInstanceName(), accessToken.getAccessToken());
                    accountList.add(CreateAccountStateKt.createAccountState(client));
                    AccountConfig accountConfig = CreateAccountConfigKt.createAccountConfig(
                            new Accounts(client),
                            accessToken.getAccessToken(),
                            appRegistration.getClientId(),
                            appRegistration.getClientSecret(),
                            client.getInstanceName());
                    AddAccountToConfigKt.addAccountToConfig(config, accountConfig);
                });

    }


}

