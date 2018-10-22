package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.Store
import com.github.OpenICP_BR.ktApp.ViewWithStage
import com.github.OpenICP_BR.ktApp.openOnNewWindow
import com.github.OpenICP_BR.ktLib.Certificate
import com.github.OpenICP_BR.ktLib.TESTING_ROOT_CA_SUBJECT
import javafx.event.ActionEvent
import javafx.scene.control.Alert
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.image.Image
import javafx.scene.layout.Region
import tornadofx.*
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.Stage
import main.kotlin.ktApp.views.AboutView
import main.kotlin.ktApp.views.GenCertView
import main.kotlin.ktApp.views.SignView
import java.util.*


class MainView : ViewWithStage() {
    override var myStage: Stage? = null
    override val root : VBox by fxml("/main.fxml")

    var language : String = FX.locale.toString()
    val signView : SignView = SignView()
    val tainted_warn : VBox by fxid("TaintedWarning")
    val mainMenu : MenuBar by fxid("MainMenu")
    val helpMenu : Menu by fxid("HelpMenu")

    init {
        myStage = primaryStage
        // Enable communication between the views and allow FXML to work
        signView.fxmlLoader = this.fxmlLoader
        signView.master = this
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
        this.title = "OpenICP-BR"

        // Fix macOS menu bar
        val os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            println("Seting macOS menu")
            mainMenu.isUseSystemMenuBar = true
        }

        // Show tabs
        signView.onBeforeShow()
    }

    fun switchLangToPT(evt : ActionEvent) {
        this.onSwitchLanguage(Locale("pt"))
    }

    fun switchLangToEN(evt : ActionEvent) {
        this.onSwitchLanguage(Locale("en"))
    }

    fun onSwitchLanguage(locale : Locale) {
        // Do not change the language to the same one
        if (language == locale.toString()) {
            return
        }
        println("Switching language to: "+locale.toString())

        FX.locale = locale
        FX.messages = ResourceBundle.getBundle("Messages", locale, FXResourceBundleControl)
        this.messages = FX.messages
        val new_view = MainView()
        new_view.onBeforeShow()
        this.replaceWith(new_view)
    }

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
        val cert = Certificate(file.absolutePath)

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

    fun showGenTestingCert(evt : ActionEvent) {
        openOnNewWindow(GenCertView())
    }

    fun showAbout(evt : ActionEvent) {
        openOnNewWindow(AboutView())
    }
}