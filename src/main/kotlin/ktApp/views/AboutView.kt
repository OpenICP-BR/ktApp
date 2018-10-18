package main.kotlin.ktApp.views

import com.github.OpenICP_BR.ktApp.views.MainView
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import tornadofx.View
import java.util.*

class AboutView() : View() {
    override val root : GridPane by fxid("AboutTab")
    lateinit var master : MainView

    val kt_app_ver : Label by fxid("AboutKtAppVerLbl")
    val kt_lib_ver : Label by fxid("AboutKtLibVerLbl")
    val java_ver : Label by fxid("AboutJavaVerLbl")

    override fun onBeforeShow() {
        super.onBeforeShow()

        var properties = Properties()
        properties.load(AboutView::class.java.getResourceAsStream("/info.properties"));

        kt_app_ver.text = properties.getProperty("version")
        java_ver.text = System.getProperty("java.version")
    }
}