package com.github.OpenICP_BR.ktApp

import com.github.OpenICP_BR.ktApp.views.*
import com.github.OpenICP_BR.ktLib.CAStore

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Menu
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*
import java.util.*

val Store = CAStore()

class MyApp: App(SplashView::class) {
    companion object {
        lateinit var instance : MyApp
        var splashView = SplashView()
        var mainView: MainView? = null
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
        // Show splash screen
        stage.initStyle(StageStyle.UNDECORATED)
        super.start(stage)
        openOnNewWindow(splashView, stage)
        splashView.myStage?.toFront()
        println("shown spalsh screen")

        // Prepare stuff
        runAsync {
            println("waiting")
            splashView.status = "Just waiting"
            Thread.sleep(3000)
            splashView.status = "More waiting"
            Thread.sleep(3000)
            splashView.status = "Finished waiting"

            // Create mainView
            mainView = MainView()
        } ui {
            // Show it
            splashView.close()
            openOnNewWindow(mainView!!)
        }

    }
}

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    println("args: "+args.toString())
    println("java.home    "+System.getProperty("java.home"))
    println("java.version "+System.getProperty("java.version"))
    println("java.vendor  "+System.getProperty("java.vendor"))
    println("os.name      "+System.getProperty("os.name"))
    println("os.arch      "+System.getProperty("os.arch"))
    println("os.version   "+System.getProperty("os.version"))
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
