package com.github.OpenICP_BR.ktApp

import com.github.OpenICP_BR.ktLib.CAStore

import com.github.OpenICP_BR.ktApp.views.MainView
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.App
import tornadofx.View
import java.awt.SplashScreen

val Store = CAStore()

class MyApp: App(MainView::class) {
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
    openOnNewWindow(view as View, stage)
}

fun openOnNewWindow(view: View, stage: Stage) {
    view.onBeforeShow()
    val scene = Scene(view.root)
    stage.scene = scene
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