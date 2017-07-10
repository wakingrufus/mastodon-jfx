package com.github.wakingrufus.mastodon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.wakingrufus.mastodon.client.ClientBuilder;
import com.github.wakingrufus.mastodon.config.Config;
import com.github.wakingrufus.mastodon.config.FileConfig;
import com.github.wakingrufus.mastodon.ui.UiService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        ClientBuilder clientService = new ClientBuilder();
        File homeDir = new File(System.getProperty("user.home"));
        File configFile = new File(homeDir, ".mastodon.txt");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new KotlinModule());
        Config config = new FileConfig(configFile, objectMapper);
        UiService uiService = new UiService(stage, clientService, config);
        uiService.init();

    }
}
