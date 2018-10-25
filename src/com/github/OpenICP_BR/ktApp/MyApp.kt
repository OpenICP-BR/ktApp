package com.github.OpenICP_BR.ktApp

import com.github.OpenICP_BR.ktLib.CAStore

import com.github.OpenICP_BR.ktApp.views.MainView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Menu
import javafx.stage.Stage
import com.github.OpenICP_BR.ktApp.views.ViewWithStage
import com.github.OpenICP_BR.ktApp.views.AboutView
import com.github.OpenICP_BR.ktApp.views.GenCertView
import tornadofx.App
import tornadofx.FX
import tornadofx.View
import java.util.*

val Store = CAStore()

class MyApp: App(MainView::class) {
    companion object {
        lateinit var instance : MyApp
        var aboutView : AboutView? = null
        var genCertView : GenCertView? = null
        var defaultApplicationMenu: Menu? = null
    }

    init {
        instance = this
        // Ensure we always use English or Portuguese
        try {
            if (FX.locale.language != "pt") {
                FX.locale = Locale("en", "US")
                Locale.setDefault(FX.locale)
            }
        } catch (e: Exception) {}
    }

    override fun start(stage: Stage) {
        super.start(stage)
        openOnNewWindow(MainView(), stage)
    }
}

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

fun openOnNewWindow(view: ViewWithStage, stage: Stage) {
    view.myStage = stage
    if (view.root.scene == null) {
        stage.scene = Scene(view.root)
    } else {
        stage.scene = view.root.scene
    }
    openOnNewWindow(view as View, stage)
}

fun openOnNewWindow(view: View, stage: Stage) {
    view.onBeforeShow()
    stage.show()
}

fun openOnNewWindow(view: ViewWithStage) {
    val stage = Stage()
    openOnNewWindow(view, stage)
}

fun openOnNewWindow(view: View) {
    val stage = Stage()
    openOnNewWindow(view, stage)
}
