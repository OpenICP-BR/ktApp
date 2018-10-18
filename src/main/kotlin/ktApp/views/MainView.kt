package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.Store
import com.github.OpenICP_BR.ktLib.Certificate
import com.github.OpenICP_BR.ktLib.TESTING_ROOT_CA_SUBJECT
import javafx.event.ActionEvent
import javafx.scene.control.Alert
import javafx.scene.control.MenuBar
import javafx.scene.image.Image
import javafx.scene.layout.Region
import tornadofx.*
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import main.kotlin.ktApp.views.AboutView
import main.kotlin.ktApp.views.AdvView
import main.kotlin.ktApp.views.SignView


class MainView : View() {
    override val root : VBox by fxml("/main.fxml")
    val signView : SignView = SignView()
    val advView : AdvView = AdvView()
    val aboutView : AboutView = AboutView()
    val tainted_warn : VBox by fxid("TaintedWarning")
    val mainMenu : MenuBar by fxid("MainMenu")

    init {
        // Enable communication between the views and allow FXML to work
        signView.fxmlLoader = this.fxmlLoader
        signView.master = this
        advView.fxmlLoader = this.fxmlLoader
        advView.master = this
        aboutView.fxmlLoader = this.fxmlLoader
        aboutView.master = this
    }

    override fun onBeforeShow() {
        super.onBeforeShow()

        // Set icon
        var res = MainView::class.java.getResourceAsStream("/logo.svg")
        this.primaryStage.icons.add(Image(res));
        res = MainView::class.java.getResourceAsStream("/logo-64x64.png")
        this.primaryStage.icons.add(Image(res));
        res = MainView::class.java.getResourceAsStream("/logo-48x48.png")
        this.primaryStage.icons.add(Image(res));
        res = MainView::class.java.getResourceAsStream("/logo-32x32.png")
        this.primaryStage.icons.add(Image(res));
        res = MainView::class.java.getResourceAsStream("/logo-16x16.png")
        this.primaryStage.icons.add(Image(res));

        // Set app name
        this.primaryStage.titleProperty().unbind()
        this.primaryStage.title = "OpenICP-BR"

        // Fix macOS menu
        val os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            mainMenu.useSystemMenuBarProperty().set(true)
        }

        // Show tabs
        signView.onBeforeShow()
        advView.onBeforeShow()
        aboutView.onBeforeShow()
    }

    fun switchLangToPT(evt : ActionEvent) {
        this.onSwitchLanguage("pt")
    }

    fun switchLangToEN(evt : ActionEvent) {
        this.onSwitchLanguage("en")
    }

    fun onSwitchLanguage(lang : String ) {}

    fun showImportRootCA(evt : ActionEvent) {
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
            tainted_warn.minHeight = Region.USE_COMPUTED_SIZE
            tainted_warn.prefHeight = Region.USE_COMPUTED_SIZE
            tainted_warn.maxHeight = Region.USE_COMPUTED_SIZE
        } catch (e: IllegalArgumentException) {
            // Show an error message
            val alert = Alert(Alert.AlertType.ERROR)
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

    fun showGenTestingCert(evt : ActionEvent) {}

    fun showAbout(evt : ActionEvent) {}
}