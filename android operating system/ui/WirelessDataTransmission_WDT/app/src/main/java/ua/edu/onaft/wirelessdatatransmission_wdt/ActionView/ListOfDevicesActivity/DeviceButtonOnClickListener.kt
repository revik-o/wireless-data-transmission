package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ListOfDevicesActivity

import android.view.View
import com.WDTComponents.AppConfig
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import java.io.File
import java.net.Socket

class DeviceButtonOnClickListener(socket: Socket): View.OnClickListener {

    val socket: Socket = socket

    override fun onClick(v: View?) {
        var sendType = 0
        for (file in SessionState.chosenFiles) {
            if (file.isDirectory) {
                sendType = 2
                break
            }
            else sendType = 1
        }
        val tempFiles = ArrayList<File>(SessionState.chosenFiles)
        SessionState.chosenFiles.clear()
        if (sendType == 1) {
            Thread {
                AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType1(socket, tempFiles)
            }.start()
        } else if (sendType == 2) {
            Thread {
                AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType2(socket, tempFiles)
            }.start()
        }
    }

}