package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ListOfDevicesActivity

import android.view.View
import com.WDTComponents.AppConfig
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import java.net.Socket

class DeviceButtonOnClickListener(socket: Socket): View.OnClickListener {

    val socket: Socket = socket

    override fun onClick(v: View?) {
        var sendType = 0
        for (file in SessionState.files) {
            if (file.isDirectory) {
                sendType = 2
                break
            }
            else sendType = 1
        }
        if (sendType == 1) {
            Thread {
                AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType1(socket, SessionState.files)
            }.start()
        } else if (sendType == 2) {
            Thread {
                AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType2(socket, SessionState.files)
            }.start()
        }
    }

}