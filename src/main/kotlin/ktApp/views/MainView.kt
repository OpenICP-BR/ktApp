package com.github.OpenICP_BR.ktApp.views

import javafx.scene.image.Image
import tornadofx.*
import javafx.scene.layout.VBox
import main.kotlin.ktApp.views.AdvView
import main.kotlin.ktApp.views.SignView
import java.nio.file.Files
import java.util.stream.Stream
import java.nio.file.Paths
import java.nio.file.FileSystems
import java.nio.file.Path
import java.util.*


class MainView : View() {
    override val root : VBox by fxml("/main.fxml")
    val signView : SignView = SignView()
    val advView : AdvView = AdvView()
    val tainted_warn : VBox by fxid("TaintedWarning")

    init {
        // Enable communication between the views and allow FXML to work
        signView.fxmlLoader = this.fxmlLoader
        signView.master = this
        advView.fxmlLoader = this.fxmlLoader
        advView.master = this

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

        // List all reource files
        val uri = MainView::class.java!!.getResource("").toURI()
        val myPath: Path
        if (uri.getScheme().equals("jar")) {
            val fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap<String, Object>())
            myPath = fileSystem.getPath("")
        } else {
            println(uri)
            myPath = Paths.get(uri)
        }
        val walk = Files.walk(myPath, 1)
        val it = walk.iterator()
        while (it.hasNext()) {
            System.out.println(it.next())
        }
    }

    override fun onBeforeShow() {
        super.onBeforeShow()

        // Set app name
        this.primaryStage.titleProperty().unbind()
        this.primaryStage.title = "OpenICP-BR"

        signView.onBeforeShow()
        advView.onBeforeShow()
    }
}