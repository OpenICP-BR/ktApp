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
}
