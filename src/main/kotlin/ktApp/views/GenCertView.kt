package main.kotlin.ktApp.views

import com.github.OpenICP_BR.ktApp.Store
import com.github.OpenICP_BR.ktApp.ViewWithStage
import com.github.OpenICP_BR.ktApp.views.MainView
import com.github.OpenICP_BR.ktLib.Certificate
import com.github.OpenICP_BR.ktLib.TESTING_ROOT_CA_SUBJECT
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region
import javafx.stage.FileChooser
import javafx.stage.Stage
import tornadofx.View
import tornadofx.get
import tornadofx.hide
import java.time.LocalDate

class GenCertView() : ViewWithStage() {
    override var myStage: Stage? = null
    override val root : GridPane by fxml("/gen_cert.fxml")

    val type : ComboBox<String> by fxid("AdvPanelType")
    val bar : ProgressBar by fxid("AdvPanelProgressBar")
    val status : Label by fxid("AdvPanelStatus")
    val name : TextField by fxid("AdvPanelName")
    val pass : TextField by fxid("AdvPanelPassword")
    val issuer_pfx: TextField by fxid("AdvPanelIssuerPFX")
    val issuer_btn: Button by fxid("AdvPanelIssuerPFXBtn")
    val issuer_pass : TextField by fxid("AdvPanelIssuerPassword")
    val not_before : DatePicker by fxid("AdvPanelNotBefore")
    val not_after : DatePicker by fxid("AdvPanelNotAfter")
    val gen_btn : Button by fxid("AdvPanelGenBtn")
    var old_name: String? = ""

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
        not_before.value = LocalDate.now()
        not_after.value = not_before.value.plusYears(1)
    }

    fun onType(event: ActionEvent) {
        // Root CA
        if (type.selectionModel.selectedIndex == 0) {
            name.isDisable = true
            if (name.text != TESTING_ROOT_CA_SUBJECT) {
                old_name = name.text
            }
            name.text = TESTING_ROOT_CA_SUBJECT
            issuer_pass.isDisable = true
            issuer_pfx.isDisable = true
            issuer_btn.isDisable = true
        } else {
            name.isDisable = false
            if (name.text == TESTING_ROOT_CA_SUBJECT) {
                name.text = old_name
            }
            issuer_pass.isDisable = false
            issuer_pfx.isDisable = false
            issuer_btn.isDisable = false
        }
    }

    fun onGenBtn(event: ActionEvent) {
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

    fun onSelectIssuer(event: ActionEvent) {
        // Select output file
        val fileChooser = FileChooser()
        fileChooser.title = ""
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter(this.messages["T.PFXFile"], "*.pfx, *.p12"))
        var file = fileChooser.showOpenDialog(root.scene.window)
        if (file == null) {
            return
        }
        println(file)
    }
}