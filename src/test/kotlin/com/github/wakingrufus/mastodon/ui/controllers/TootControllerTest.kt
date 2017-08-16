import com.github.wakingrufus.mastodon.account.AccountState
import com.github.wakingrufus.mastodon.ui.controllers.TootController
import com.nhaarman.mockito_kotlin.mock
import com.sys1yagi.mastodon4j.api.entity.Account
import com.sys1yagi.mastodon4j.api.entity.Status
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import mu.KLogging
import org.junit.Test
import org.testfx.api.FxAssert.verifyThat
import org.testfx.framework.junit.ApplicationTest
import org.testfx.matcher.base.NodeMatchers
import java.io.IOException

class TootControllerTest : ApplicationTest() {
    companion object : KLogging()

    @Test
    @Throws(IOException::class)
    fun test() {
        logger.info("TootControllerTest")
        verifyThat<Node>("#accountView", NodeMatchers.isVisible())
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        val account: Account = Account(displayName = "displayName")
        val status: Status = Status(account = account)
        val tootController = TootController(
                status = status,
                accountPrompter = { AccountState(account = account, client = mock()) })
        val fxmlLoader = FXMLLoader(javaClass.getResource("/toot.fxml"))
        fxmlLoader.setController(tootController)
        val load: Parent = fxmlLoader.load()
        val scene = Scene(load, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }
}