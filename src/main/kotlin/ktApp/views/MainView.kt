package com.github.OpenICP_BR.ktApp.views

import com.github.OpenICP_BR.ktApp.Styles
import javafx.scene.control.Alert.AlertType.INFORMATION
import tornadofx.*
import com.github.OpenICP_BR.ICP.TESTING_ROOT_CA_SUBJECT

class MainView : View("Hello TornadoFX") {
    override val root = borderpane {
        addClass(Styles.welcomeScreen)
        top {
            stackpane {
                label(title).addClass(Styles.heading)
            }
        }
        center {
            stackpane {
                addClass(Styles.content)
                button("Click me") {
                    setOnAction {
                        alert(INFORMATION, "Well done!", "You clicked me! " +TESTING_ROOT_CA_SUBJECT)
                    }
                }
            }
        }
    }
}