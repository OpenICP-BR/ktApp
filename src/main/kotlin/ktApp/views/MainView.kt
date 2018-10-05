package com.github.OpenICP_BR.ktApp.views

import tornadofx.*
import javafx.scene.control.ComboBox
import javafx.scene.control.TabPane
import javafx.collections.FXCollections
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar


class MainView : View() {
    override val root : TabPane by fxml("/main.fxml")
    val state : ComboBox<String> by fxid("SignPanelState")
    val country : ComboBox<String> by fxid("SignPanelCountry")
    val adv_types : ComboBox<String> by fxid("AdvPanelType")
    val sign_bar : ProgressBar by fxid("SignPanelProgressBar")
    val sign_status : Label by fxid("SignPanelStatus")
    val adv_bar : ProgressBar by fxid("AdvPanelProgressBar")
    val adv_status : Label by fxid("AdvPanelStatus")

    override fun onBeforeShow() {
        super.onBeforeShow()
        // Add states and Countries
        state.items = FXCollections.observableArrayList(this.messages["BR.States"].split(";"))
        country.items = FXCollections.observableArrayList(this.messages["INT.Countries"].split(";"))
        // Select Brazil
        country.selectionModel.select(32)

        // Add certificate types
        adv_types.items = FXCollections.observableArrayList(this.messages["Adv.Types"].split(";"))

        // Hide progress bars and statuses
        sign_bar.hide()
        sign_status.hide()
        adv_bar.hide()
        adv_status.hide()

    }
}