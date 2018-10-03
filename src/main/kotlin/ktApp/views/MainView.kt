package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.Styles
import javafx.scene.control.Alert.AlertType.INFORMATION
import tornadofx.*
import com.github.OpenICP_BR.ICP.TESTING_ROOT_CA_SUBJECT
import javafx.scene.control.ComboBox
import javafx.scene.control.TabPane
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ChoiceBox


class MainView : View() {
    override val root : TabPane by fxml("/main.fxml")
    val ufs : ComboBox<String> by fxid("SignPanelStates")
    val countries : ComboBox<String> by fxid("SignPanelCountries")

    override fun onBeforeShow() {
        super.onBeforeShow()
        // Add states and Countries
        ufs.setItems(FXCollections.observableArrayList(this.messages["BR.States"].split(";")))
        countries.setItems(FXCollections.observableArrayList(this.messages["INT.Countries"].split(";")))
    }
}