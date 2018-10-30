package com.github.OpenICP_BR.ktApp.views

import javafx.event.ActionEvent
import javafx.scene.control.*
import javafx.scene.input.Clipboard
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser
import tornadofx.View
import tornadofx.get
import tornadofx.hide
import tornadofx.putString

class CertsView() : View() {
    override val root : SplitPane by fxid("CertsTab")
    lateinit var master : MainView

    val certsPanelInfo : GridPane by fxid("CertsPanelInfo")
    val certsPanelList : ListView<String> by fxid("CertsPanelList")

    val addBtn : Button by fxid("CertsPanelAddBtn")
    val delBtn : Button by fxid("CertsPanelDelBtn")
    val copySubjectBtn : Button by fxid("CertsPanelCopySubjectBtn")
    val copyIssuerBtn : Button by fxid("CertsPanelCopyIssuerBtn")
    val copyRootCABtn : Button by fxid("CertsPanelCopyRootCABtn")
    val copyValidityBtn : Button by fxid("CertsPanelCopyValidityBtn")
    val saveCopyBtn : Button by fxid("CertsPanelCopyBtn")

    val subjectLbl : Label by fxid("lblCertSubject")
    val issuerLbl : Label by fxid("lblCertIssuer")
    val rootCALbl : Label by fxid("lblCertRootCA")
    val validityLbl : Label by fxid("lblCertValidity")

    override fun onBeforeShow() {
        super.onBeforeShow()

        certsPanelList.items.add("hi")
        certsPanelList.items.add("hi2")

        // Bind events
        addBtn.setOnAction { e -> onFileSelect(e) }
        copySubjectBtn.setOnAction { e -> copyToClipboard(subjectLbl.text) }
        copyIssuerBtn.setOnAction { e -> copyToClipboard(issuerLbl.text) }
        copyRootCABtn.setOnAction { e -> copyToClipboard(rootCALbl.text) }
        copyValidityBtn.setOnAction { e -> copyToClipboard(validityLbl.text) }
    }

    fun copyToClipboard(str : String) {
        Clipboard.getSystemClipboard().putString(str)
    }

    fun onFileSelect(event: ActionEvent) {
        println("HI!!!")
        // Ask user to select the file
        val fileChooser = FileChooser()
        fileChooser.title = this.messages["Sign.SelectFile"]
        val files = fileChooser.showOpenMultipleDialog(root.scene.window)
        if (files == null) {
            return
        }
        println(files)
    }
}