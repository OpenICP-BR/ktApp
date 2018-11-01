package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.views.ViewWithStage
import com.github.OpenICP_BR.ktLib.TESTING_ROOT_CA_SUBJECT
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import tornadofx.get
import tornadofx.hide
import tornadofx.selectedItem
import java.time.LocalDate

enum class CertType {
    ROOT, INTERMEDIATE, FINAL, TSA
}

class GenCertView() : ViewWithStage() {
    override var myStage: Stage? = null
    override val root : GridPane by fxml("/gen_cert.fxml")

    val type : ComboBox<String> by fxid("AdvPanelType")
    val bar : ProgressBar by fxid("AdvPanelProgressBar")
    val status : Label by fxid("AdvPanelStatus")
    val name : TextField by fxid("AdvPanelName")
    val pass : TextField by fxid("AdvPanelPassword")
    val issuerPfx: TextField by fxid("AdvPanelIssuerPFX")
    val issuerBtn: Button by fxid("AdvPanelIssuerPFXBtn")
    val issuerPass : TextField by fxid("AdvPanelIssuerPassword")
    val notBefore : DatePicker by fxid("AdvPanelNotBefore")
    val notAfter : DatePicker by fxid("AdvPanelNotAfter")
    val genBtn : Button by fxid("AdvPanelGenBtn")
    var oldName: String? = ""
    val selectedType: CertType
    get() {
        when (type.selectionModel.selectedIndex) {
            0 -> return CertType.ROOT
            1 -> return CertType.INTERMEDIATE
            2 -> return CertType.FINAL
            3 -> return CertType.TSA
            else -> throw Exception("unknown cert type: ${type.selectedItem} (${type.selectionModel.selectedIndex})")
        }

    }

    override fun onBeforeShow() {
        super.onBeforeShow()

        // Fix stage
        myStage?.titleProperty()?.unbind()
        myStage?.title = messages["T.GenCert"]

        // Add certificate types
        type.items = FXCollections.observableArrayList(this.messages["Adv.Types"].split(";"))

        // Hide progress bars and statuses
        bar.hide()
        status.hide()

        // Set validity from today to a year after today
        notBefore.value = LocalDate.now()
        notAfter.value = notBefore.value.plusYears(1)

        // Bind events
        genBtn.setOnAction { e -> generateCert() }
        issuerBtn.setOnAction { e -> issuerFileOpener() }
    }

    fun onType() {
        // Root CA
        if (selectedType == CertType.ROOT) {
            name.isDisable = true
            if (name.text != TESTING_ROOT_CA_SUBJECT) {
                oldName = name.text
            }
            name.text = TESTING_ROOT_CA_SUBJECT
            issuerPass.isDisable = true
            issuerPfx.isDisable = true
            issuerBtn.isDisable = true
        } else {
            name.isDisable = false
            if (name.text == TESTING_ROOT_CA_SUBJECT) {
                name.text = oldName
            }
            issuerPass.isDisable = false
            issuerPfx.isDisable = false
            issuerBtn.isDisable = false
        }
    }

    fun generateCert() {
        // Select output file
        val fileChooser = FileChooser()
        fileChooser.title = ""
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter(this.messages["T.PFXFile"], "*.pfx, *.p12"))
        val file = fileChooser.showSaveDialog(root.scene.window)
        if (file == null) {
            return
        }
        throw Exception("feature not implemented")
    }

    fun issuerFileOpener() {
        // Select output file
        val fileChooser = FileChooser()
        fileChooser.title = ""
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter(this.messages["T.PFXFile"], "*.pfx, *.p12"))
        val file = fileChooser.showOpenDialog(root.scene.window)
        if (file == null) {
            return
        }
        println(file)
    }
}