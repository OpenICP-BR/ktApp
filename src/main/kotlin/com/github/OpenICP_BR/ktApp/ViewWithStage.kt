package com.github.OpenICP_BR.ktApp

import javafx.stage.Stage
import tornadofx.View

abstract class ViewWithStage(): View() {
    abstract var myStage : Stage?
}
