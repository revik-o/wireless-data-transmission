package app.Common.CommonMethod

import app.Action.DeviceButtonAction.DeviceButtonClipboardTypeAction
import app.Action.DeviceButtonAction.DeviceButtonFileTypeAction
import app.Common.Session
import app.Controller.TemplateController.DeviceButtonController
import com.WDTComponents.DelegateMethods.IDelegateMethodIntegerArg
import com.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import com.WDTComponents.WorkingWithDevices.ScanDevicesIPVersion4
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
import javafx.scene.layout.AnchorPane
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.locks.ReentrantLock

object ScanDevices {

    private var scanDevices: ScanDevicesIPVersion4? = null

    private var space = 0.0

    fun startScanDevice(anchorPane: AnchorPane, progressBar: ProgressBar, type: Int) {
        anchorPane.children.clear()
        progressBar.progress = 0.0
        progressBar.isVisible = true
        space = 0.0
        scanDevices = ScanDevicesIPVersion4(
            object : IDelegateMethodSocketAction {
                override fun voidMethod(
                    nameDevice: String,
                    typeDevice: String,
                    ipStaring: String,
                    dataInputStream: DataInputStream,
                    dataOutputStream: DataOutputStream,
                    socket: Socket
                ) {
                    Platform.runLater {
                        val fxmlLoader = FXMLLoader(ScanDevices::class.java.classLoader.getResource(
                            "resource/FXML/template/device_button.fxml"
                        ))
                        fxmlLoader.setController(DeviceButtonController(typeDevice, nameDevice))
                        val deviceButton: Button = fxmlLoader.load()
                        deviceButton.onAction = when (type) {
                            1 -> DeviceButtonFileTypeAction(socket, Session.fileArray)
                            2 -> DeviceButtonClipboardTypeAction(socket)
                            else -> EventHandler {}
                        }
                        deviceButton.layoutY = space
                        space += 85
                        anchorPane.children.add(deviceButton)
                    }
                }
            },
            object : IDelegateMethodIntegerArg {
                override fun voidMethod(number: Int) {
                    Platform.runLater {
                        progressBar.progress = number / 100.0
                    }
                }
            }
        )
    }

    fun stop() {
        scanDevices?.stopScanDevices()
    }

}