package com.github.OpenICP_BR.ktApp.views

import tornadofx.*
import javafx.scene.layout.VBox
import main.kotlin.ktApp.views.AdvView
import main.kotlin.ktApp.views.SignView

class MainView : View() {
    override val root : VBox by fxml("/main.fxml")
    val signView : SignView = SignView()
    val advView : AdvView = AdvView()
    val tainted_warn : VBox by fxid("TaintedWarning")

    init {
        // Enable communication between the views and allow FXML to work
        signView.fxmlLoader = this.fxmlLoader
        signView.master = this
        advView.fxmlLoader = this.fxmlLoader
        advView.master = this
    }

    override fun onBeforeShow() {
        super.onBeforeShow()

        // Set app name
        this.primaryStage.titleProperty().unbind()
        this.primaryStage.title = "OpenICP-BR"

        signView.onBeforeShow()
        advView.onBeforeShow()
    }
}