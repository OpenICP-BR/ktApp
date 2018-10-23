package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.MyApp
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
import de.codecentric.centerdevice.MenuToolkit
import javafx.scene.Scene


class MainView : ViewWithStage() {
    override var myStage: Stage? = null
    override val root : VBox by fxml("/main.fxml")

    val signView : SignView = SignView()
    val tainted_warn : VBox by fxid("TaintedWarning")
    val menuBar : MenuBar by fxid("MainMenu")

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
        MyApp.instance.hi()

        menuBar.isUseSystemMenuBar = true
        val os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            println("Seting macOS menu")
            // Create about stage and view
            val stage = Stage()
            if (MyApp.aboutView == null) {
                MyApp.aboutView = AboutView()
            }
            if (MyApp.aboutView!!.root.scene == null) {
                stage.scene = Scene(MyApp.aboutView!!.root)
            } else {
                stage.scene = MyApp.aboutView!!.root.scene
            }
            MyApp.aboutView!!.onBeforeShow()
            // Ensure OSX menubar
            MyApp.aboutView!!.myStage = stage
            val tk = MenuToolkit.toolkit()
            MyApp.defaultApplicationMenu = tk.createDefaultApplicationMenu("OpenICP-BR", MyApp.aboutView!!.myStage)
            menuBar.menus.add(0, MyApp.defaultApplicationMenu)
            tk.setGlobalMenuBar(menuBar)
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
        println("Switching language to: "+locale.toString() + "(was: "+FX.locale.toString().toString()+")")
        // Do not change the language to the same one
        if (FX.locale.toString() == locale.toString()) {
            println("No need to change language")
            return
        }

        // Close other windows
        MyApp.aboutView?.myStage?.close()
        MyApp.genCertView?.myStage?.close()
        MyApp.aboutView = null
        MyApp.genCertView = null

        // Change locale
        FX.locale = locale
        FX.messages = ResourceBundle.getBundle("Messages", locale, FXResourceBundleControl)
        this.messages = FX.messages

        // Open new main window
        val newView = MainView()
        newView.onBeforeShow()
        newView.myStage = this.myStage
        openOnNewWindow(MainView())
        this.close()
    }

    fun showImportRootCA(evt : ActionEvent) {
        // Ask user to select the file
        val fileChooser = FileChooser()
        fileChooser.title = this.messages["Adv.SelectTestingRootCA"]
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter(
                this.messages["T.CertificateFile"],
                "*.crt", "*.pem", "*.cert"))
        val file = fileChooser.showOpenDialog(root.scene.window)
        if (file == null) {
            println("no file selected")
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
        if (MyApp.genCertView == null) {
            MyApp.genCertView = GenCertView()
        }
        if (MyApp.genCertView!!.myStage == null) {
            openOnNewWindow(MyApp.genCertView!!)
        } else {
            MyApp.genCertView!!.myStage!!.show()
            MyApp.genCertView!!.myStage!!.requestFocus()
        }
    }

    fun showAbout(evt : ActionEvent) {
        if (MyApp.aboutView == null) {
            MyApp.aboutView = AboutView()
        }
        if (MyApp.aboutView!!.myStage == null) {
            openOnNewWindow(MyApp.aboutView!!)
        } else {
            MyApp.aboutView!!.myStage!!.show()
            MyApp.aboutView!!.myStage!!.requestFocus()
        }
    }
}