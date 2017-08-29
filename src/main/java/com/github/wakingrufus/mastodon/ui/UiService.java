package com.github.wakingrufus.mastodon.ui;

import com.github.wakingrufus.mastodon.EventHandlersKt;
import com.github.wakingrufus.mastodon.LoginEventHandlersKt;
import com.github.wakingrufus.mastodon.account.AccountConfig;
import com.github.wakingrufus.mastodon.account.AddAccountToConfigKt;
import com.github.wakingrufus.mastodon.account.CreateAccountConfigKt;
import com.github.wakingrufus.mastodon.account.CreateAccountStateKt;
import com.github.wakingrufus.mastodon.client.ClientBuilderKt;
import com.github.wakingrufus.mastodon.config.Config;
import com.github.wakingrufus.mastodon.data.AccountState;
import com.github.wakingrufus.mastodon.data.StatusFeed;
import com.github.wakingrufus.mastodon.events.NewAccountEvent;
import com.github.wakingrufus.mastodon.events.ViewFeedEventKt;
import com.github.wakingrufus.mastodon.events.ViewNotificationsEvent;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.method.Accounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final Config config;
    private Pane conversationBox;


    public UiService(Stage stage, Config config) {
        this.stage = stage;
        this.config = config;
    }

    public void init() {

        final ObservableList<AccountState> accountList = FXCollections.observableArrayList();
        final ObservableList<StatusFeed> feeds = FXCollections.observableArrayList();

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

        //TODO add an event to trigger this
        ViewAccountFeedsKt.viewAccountFeeds(conversationBox, feeds, accountList);

        config.getConfig().getIdentities().forEach(
                identityAuth -> {
                    MastodonClient client = ClientBuilderKt.createAccountClient(identityAuth.getServer(), identityAuth.getAccessToken());
                    accountList.add(CreateAccountStateKt.createAccountState(client));
                });
        EventHandlersKt.attachEventHandlers(root);

        root.addEventHandler(ViewFeedEventKt.getVIEW_FEED(), viewFeedEvent -> feeds.add(viewFeedEvent.getFeed()));
        root.addEventHandler(ViewNotificationsEvent.VIEW_NOTIFICATIONS,
                event -> ViewAccountNotificationsKt.viewAccountNotifications(notificationBox, event.getFeed(), accountList));

        root.addEventHandler(NewAccountEvent.NEW_ACCOUNT,
                newAccountEvent -> {
                    MastodonClient client = ClientBuilderKt.createAccountClient(newAccountEvent.getServer(), newAccountEvent.getAccessToken());
                    accountList.add(CreateAccountStateKt.createAccountState(client));
                    AccountConfig accountConfig = CreateAccountConfigKt.createAccountConfig(
                            new Accounts(client),
                            newAccountEvent.getAccessToken(),
                            newAccountEvent.getClientId(),
                            newAccountEvent.getClientSecret(),
                            newAccountEvent.getServer());
                    AddAccountToConfigKt.addAccountToConfig(config, accountConfig);
                });
        final BorderPane rootPane = root;
        LoginEventHandlersKt.attachLoginEventHandlersFromJava(p -> {
            rootPane.setCenter(p);
            return p;
        }, rootPane, accountList, config);
    }
}

