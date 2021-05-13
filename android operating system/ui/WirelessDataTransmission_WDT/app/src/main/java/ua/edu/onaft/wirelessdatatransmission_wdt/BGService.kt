package ua.edu.onaft.wirelessdatatransmission_wdt

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption

class BGService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!AppOption.SERVER_SOCKET_IS_ON)
            AppConfig.ServerControllInterface.iServer.startServerSocket()
//        startForeground(0, null)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}