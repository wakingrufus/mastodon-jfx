package com.github.wakingrufus.mastodon

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.testfx.framework.junit.ApplicationTest
import tornadofx.UIComponent
import tornadofx.clear
import tornadofx.plusAssign
import java.time.Instant

fun UIComponent.setParams(map: Map<String, Any?>) {
    this::paramsProperty.get().value = map
}

open class TornadoFxTest : ApplicationTest() {
    lateinit var wrapper: Pane
    override fun start(stage: Stage) {
        wrapper = StackPane()
        val scene = Scene(wrapper, 800.0, 600.0)
        stage.scene = scene
        stage.show()
    }

    protected fun showView(view: UIComponent) {
        Platform.runLater {
            wrapper.clear()
            wrapper += view.root
        }
        waitFor(condition = { wrapper.children.size > 0 && wrapper.children[0] == view.root })
    }


}

fun waitFor(condition: () -> Boolean, maxMillis: Long = 10000) {
    val startTime = Instant.now()
    while (!condition() && Instant.now().isBefore(startTime.plusMillis(maxMillis))) {
        Thread.sleep(1000)
    }
}