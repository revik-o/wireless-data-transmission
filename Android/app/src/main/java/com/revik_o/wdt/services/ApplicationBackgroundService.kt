package com.revik_o.wdt.services

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.INTERNET
import android.app.Service
import android.content.Intent
import com.revik_o.common.utils.PermissionUtils.checkApplicationPermissions
import com.revik_o.core.common.CommunicationProtocol
import com.revik_o.impl.AndroidAPI
import com.revik_o.infrastructure.tcp.TCP

class ApplicationBackgroundService : Service() {

    companion object {
        @Volatile
        var IS_RUNNING = false
            @Synchronized set
    }

    private fun checkPermissions(api: AndroidAPI): Boolean =
        checkApplicationPermissions(this, INTERNET, ACCESS_NETWORK_STATE)
                && api.appSettings.isCommunicationEnabled && !IS_RUNNING

    override fun onBind(intent: Intent?): Nothing? = null

    override fun onCreate() {
        super.onCreate()
        val api = AndroidAPI(this)

        if (checkPermissions(api)) {
            when (api.appSettings.currentCommunicationProtocol) {
                CommunicationProtocol.TCP -> TCP.start(api)
            }

            IS_RUNNING = true
        }
    }

    override fun onDestroy() {
        val api = AndroidAPI(this)

        when (api.appSettings.currentCommunicationProtocol) {
            CommunicationProtocol.TCP -> TCP.stop(api)
        }

        IS_RUNNING = false

        super.onDestroy()
    }
}