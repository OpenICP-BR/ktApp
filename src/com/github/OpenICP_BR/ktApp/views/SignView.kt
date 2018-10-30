package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.views.MainView
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser
import tornadofx.View
import tornadofx.get
import tornadofx.hide

class SignView() : View() {
    override val root : GridPane by fxid("SignTab")
    lateinit var master : MainView

    val state : ComboBox<String> by fxid("SignPanelState")
    val country : ComboBox<String> by fxid("SignPanelCountry")
    val pbar : ProgressBar by fxid("SignPanelProgressBar")
    val status : Label by fxid("SignPanelStatus")
    val filesCtrl : TextInputControl by fxid("SignPanelFiles")
    val sel_btn : Button by fxid("SignPanelFilesBtn")
    val sign_btn : Button by fxid("SignPanelSignBtn")

    override fun onBeforeShow() {
        super.onBeforeShow()

        // Hide status and progress bar
        pbar.hide()
        status.hide()

        // Add states and countries
        state.items = FXCollections.observableArrayList(this.messages["BR.States"].split(";"))
        country.items = FXCollections.observableArrayList(this.messages["INT.Countries"].split(";"))
        // Select Brazil
        country.selectionModel.select(32)

        // Bind events
        sel_btn.setOnAction { e -> onFileSelect(e) }
    }

    fun onFileSelect(event: ActionEvent) {
        // Ask user to select the file
        val fileChooser = FileChooser()
        fileChooser.title = this.messages["Sign.SelectFile"]
        val files = fileChooser.showOpenMultipleDialog(root.scene.window)
        if (files == null) {
            return
        }
        println(files)
        if (files.size == 0) {
            filesCtrl.text = ""
        } else if (files.size == 1) {
            filesCtrl.text = files[0].name
        } else {
            filesCtrl.text = "%d %s".format(files.size, this.messages["T.files"])
        }
    }
}