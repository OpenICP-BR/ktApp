package com.github.OpenICP_BR.ktApp

import com.github.OpenICP_BR.ktLib.CAStore

import com.github.OpenICP_BR.ktApp.views.MainView
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.App

val Store = CAStore()

class MyApp: App(MainView::class) {
}

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}
