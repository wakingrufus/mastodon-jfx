package com.github.wakingrufus.mastodon;

import com.github.wakingrufus.mastodon.client.ClientBuilder;
import com.github.wakingrufus.mastodon.config.Config;
import com.github.wakingrufus.mastodon.ui.UiService;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

@Slf4j
public class IntegrationTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        Config config = Mockito.mock(Config.class);
        UiService uiService = new UiService(stage, new ClientBuilder(), config);
    }

    @Test
    public void should_drag_file_into_trashcan() {
        /*
        // given:


        // when:
        clickOn("#newIdButton");

        // then:
        verifyThat("#desktop", hasChildren(0, ".file"));
        */
    }


}