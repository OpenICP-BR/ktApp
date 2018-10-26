package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.views.ViewWithStage
import com.github.OpenICP_BR.ktLib.ICPVersion
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import java.util.*
import javafx.stage.Stage
import tornadofx.FX
import tornadofx.get
import java.awt.Desktop
import java.net.URI


class AboutView() : ViewWithStage() {
    override var myStage: Stage? = null
    override val root : GridPane by fxml("/about.fxml")

    val ktAppVer : Label by fxid("AboutKtAppVerLbl")
    val ktLibVer : Label by fxid("AboutKtLibVerLbl")
    val javaVer : Label by fxid("AboutJavaVerLbl")
    val osVer : Label by fxid("AboutOSLbl")
    val javaPath : Label by fxid("AboutJavaPathLbl")

    override fun onBeforeShow() {
        super.onBeforeShow()

        myStage!!.isResizable = false
        myStage!!.titleProperty()?.unbind()
        myStage!!.title = messages["T.About"]

        val properties = Properties()
        properties.load(AboutView::class.java.getResourceAsStream("/info.properties"));

        ktLibVer.text = ICPVersion
        ktAppVer.text = properties.getProperty("version")
        javaVer.text = System.getProperty("java.version") + " (" + System.getProperty("java.vendor") + ")"
        osVer.text = System.getProperty("os.name") + " - " + System.getProperty("os.arch") + " - " + System.getProperty("os.version")
        javaPath.text = System.getProperty("java.home")
    }

    fun openWebsite() {
        try {
            Desktop.getDesktop().browse(URI("https://openicp-br.github.io/" + FX.locale.language));
        } catch (e: Exception) {
        }
    }
}