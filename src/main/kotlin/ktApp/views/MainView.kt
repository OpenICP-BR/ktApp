package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ICP.TESTING_ROOT_CA_SUBJECT
import com.github.OpenICP_BR.ktApp.Store
import com.github.OpenICP_BR.ICP.Certificate
import com.github.OpenICP_BR.ICP.newCert
import com.github.OpenICP_BR.ICP.newTestRootCA
import tornadofx.*
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.control.*
import java.time.LocalDate
import javafx.stage.FileChooser
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert
import javafx.scene.layout.Region.USE_COMPUTED_SIZE
import javafx.scene.layout.VBox

class MainView : View() {
    override val root : VBox by fxml("/main.fxml")
    val sign_state : ComboBox<String> by fxid("SignPanelState")
    val sign_country : ComboBox<String> by fxid("SignPanelCountry")
    val adv_type : ComboBox<String> by fxid("AdvPanelType")
    val sign_bar : ProgressBar by fxid("SignPanelProgressBar")
    val sign_status : Label by fxid("SignPanelStatus")
    val adv_bar : ProgressBar by fxid("AdvPanelProgressBar")
    val adv_status : Label by fxid("AdvPanelStatus")
    val adv_name : TextField by fxid("AdvPanelName")
    val adv_pass : TextField by fxid("AdvPanelPassword")
    val adv_issuer_pfx: TextField by fxid("AdvPanelIssuerPFX")
    val adv_issuer_btn: Button by fxid("AdvPanelIssuerPFXBtn")
    val adv_issuer_pass : TextField by fxid("AdvPanelIssuerPassword")
    val adv_not_before : DatePicker by fxid("AdvPanelNotBefore")
    val adv_not_after : DatePicker by fxid("AdvPanelNotAfter")
    val tainted_warn : VBox by fxid("TaintedWarning")
    var adv_old_name: String? = ""

    override fun onBeforeShow() {
        super.onBeforeShow()

        // Set app name
        this.primaryStage.titleProperty().unbind()
        this.primaryStage.title = "OpenICP-BR"

        // Add states and countries
        sign_state.items = FXCollections.observableArrayList(this.messages["BR.States"].split(";"))
        sign_country.items = FXCollections.observableArrayList(this.messages["INT.Countries"].split(";"))
        // Select Brazil
        sign_country.selectionModel.select(32)

        // Add certificate types
        adv_type.items = FXCollections.observableArrayList(this.messages["Adv.Types"].split(";"))

        // Hide progress bars and statuses
        sign_bar.hide()
        sign_status.hide()
        adv_bar.hide()
        adv_status.hide()

        // Set validity from today to a year after today
        adv_not_before.value = LocalDate.now()
        adv_not_after.value = adv_not_before.value.plusYears(1)
    }

    fun onAdvType(event: ActionEvent) {
        // Root CA
        if (adv_type.selectionModel.selectedIndex == 0) {
            adv_name.isDisable = true
            if (adv_name.text != TESTING_ROOT_CA_SUBJECT) {
                adv_old_name = adv_name.text
            }
            adv_name.text = TESTING_ROOT_CA_SUBJECT
            adv_issuer_pass.isDisable = true
            adv_issuer_pfx.isDisable = true
            adv_issuer_btn.isDisable = true
        } else {
            adv_name.isDisable = false
            if (adv_name.text == TESTING_ROOT_CA_SUBJECT) {
                adv_name.text = adv_old_name
            }
            adv_issuer_pass.isDisable = false
            adv_issuer_pfx.isDisable = false
            adv_issuer_btn.isDisable = false
        }
    }

    fun onAdvAddBtn(event: ActionEvent) {
        // Ask user to select the file
        val fileChooser = FileChooser()
        fileChooser.title = this.messages["Adv.SelectTestingRootCA"]
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter(
                this.messages["T.CertificateFile"],
                "*.crt", "*.pem", "*.cert"))
        var file = fileChooser.showOpenDialog(root.scene.window)
        if (file == null) {
            return
        }
        val cert = Certificate(file.getAbsolutePath())

        // Try to add as a testing Root CA
        try {
            Store.addTestingCA(cert)
            // Alert the user
            tainted_warn.minHeight = USE_COMPUTED_SIZE
            tainted_warn.prefHeight = USE_COMPUTED_SIZE
            tainted_warn.maxHeight = USE_COMPUTED_SIZE
        } catch (e: IllegalArgumentException) {
            // Show an error message
            val alert = Alert(AlertType.ERROR)
            alert.title = this.messages["Errs.InvalidTestingCertificate.Title"]
            alert.headerText = this.messages["Errs.InvalidTestingCertificate.Header"]
            alert.contentText = this.messages["Errs.InvalidTestingCertificate.Content"].format(
                    TESTING_ROOT_CA_SUBJECT,
                    cert.fullSubject,
                    cert.fullIssuer)
            alert.isResizable = true
            alert.showAndWait()
        }
    }

    fun onAdvGenBtn(event: ActionEvent) {
        // Select output file
        val fileChooser = FileChooser()
        fileChooser.title = ""
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter(this.messages["T.PFXFile"], "*.pfx, *.p12"))
        var file = fileChooser.showSaveDialog(root.scene.window)
        if (file == null) {
            return
        }
        println(file)
    }
}