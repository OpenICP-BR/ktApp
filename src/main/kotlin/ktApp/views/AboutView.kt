package main.kotlin.ktApp.views

import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import tornadofx.View
import java.util.*
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.stage.Modality
import tornadofx.get
import java.awt.Desktop
import java.net.URI


class AboutView() : View() {
    override val root : GridPane by fxml("/about.fxml")

    val kt_app_ver : Label by fxid("AboutKtAppVerLbl")
    val kt_lib_ver : Label by fxid("AboutKtLibVerLbl")
    val java_ver : Label by fxid("AboutJavaVerLbl")
    val os_ver : Label by fxid("AboutOSLbl")

    override fun onBeforeShow() {
        super.onBeforeShow()

        val properties = Properties()
        properties.load(AboutView::class.java.getResourceAsStream("/info.properties"));

        kt_app_ver.text = properties.getProperty("version")
        java_ver.text = System.getProperty("java.version")
        os_ver.text = System.getProperty("os.name") + " - " + System.getProperty("os.version")
    }

    fun openNewWindow() {
        this.onBeforeShow()

        val scene = Scene(root)
        val stage = Stage()
        stage.title = this.messages["T.About"]
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }

    fun openWebsite() {
        try {
            Desktop.getDesktop().browse(URI("https://openicp-br.github.io"));
        } catch (e: Exception) {
        }
    }
}