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
        try {
            val splash = SplashScreen.getSplashScreen()
            println("MyApp.start 5")
            splash.close()
            println("MyApp.start 6")
        } catch (e: Exception) {
            println("MyApp.start 7")
            e.printStackTrace()
        }

        println("MyApp.start 1")
        super.start(stage)
        println("MyApp.start 2")
        Thread.sleep(1000);
        println("MyApp.start 3")
        openOnNewWindow(MainView(), stage)
        println("MyApp.start 4")

    }
}

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

fun openOnNewWindow(view: View, stage: Stage) {
    view.onBeforeShow()

    val scene = Scene(view.root)
    stage.scene = scene
    stage.show()
}

fun openOnNewWindow(view: View) {
    val stage = Stage()
    openOnNewWindow(view, stage)
}