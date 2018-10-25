package com.github.OpenICP_BR.ktApp.views

import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import javafx.stage.Stage

class SplashView : ViewWithStage() {
    override var myStage: Stage? = null
    override val root : AnchorPane by fxml("/splash.fxml")
    val splashStatusMsg : Text by fxid("SplashStatusMsg")

    var status: String
        get() {
            return splashStatusMsg.text
        }
        set(new) {
            splashStatusMsg.text = new
        }

    override fun onBeforeShow() {
    }

    fun pleaseClose() {
        println("Please close")
        myStage?.close()
        primaryStage.close()
    }

    fun pleaseDrag() {

    }
}