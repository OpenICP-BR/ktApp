package com.github.OpenICP_BR.ktApp

import com.github.OpenICP_BR.ktApp.views.MainView
import javafx.application.Application
import tornadofx.App
import java.io.File
import java.nio.file.Paths

class MyApp: App(MainView::class)

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    val path = Paths.get("").toAbsolutePath().toString()
    println("Working Directory = $path")
    println(File("res/main.fxml").exists())
    Application.launch(MyApp::class.java, *args)
}
