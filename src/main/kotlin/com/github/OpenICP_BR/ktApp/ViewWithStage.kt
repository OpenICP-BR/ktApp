package com.github.OpenICP_BR.ktApp

import javafx.beans.Observable
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.View
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import tornadofx.keyboard


abstract class ViewWithStage(): View() {
    abstract var myStage : Stage?

    override fun onBeforeShow() {
        super.onBeforeShow()

        if (myStage?.scene == null) {
            myStage?.scene = root.scene
        }

        val kc = KeyCodeCombination(KeyCode.W, KeyCombination.META_DOWN)
        var rn = { println("Accelerator key worked") }
        println(myStage)
        println(myStage?.scene)
        println(myStage?.scene?.accelerators)
        myStage?.scene?.accelerators?.put(kc, Runnable(rn));
    }
}
