package com.revik_o.wdt.background

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.INTERNET
import android.app.Service
import android.content.Intent
import com.revik_o.core.CommunicationProtocol.TCP
import com.revik_o.core.service.DeviceCommunicationServiceI
import com.revik_o.infrastructure.tcp.TCP.start
import com.revik_o.infrastructure.tcp.TCP.stop
import com.revik_o.wdt.config.ApplicationConfig.Companion.getAppConfig
import com.revik_o.wdt.utils.PermissionUtils.checkApplicationPermissions

class DeviceCommunicationBackgroundService : Service() {

    companion object {
        @Volatile
        var IS_RUNNING = false
            @Synchronized set
    }

    private val _appConfig = getAppConfig()

    private fun checkPermissions(): Boolean =
        checkApplicationPermissions(this, INTERNET, ACCESS_NETWORK_STATE)
                && _appConfig.isCommunicationEnabled && !IS_RUNNING

    override fun onBind(intent: Intent?): Nothing? = null

    override fun onCreate() {
        super.onCreate()

        if (checkPermissions()) {
            IS_RUNNING = true

            when (_appConfig.currentCommunicationProtocol) {
                TCP -> {
                    val _communicationService = TCPCommunicationService(_appConfig, )
                    start(_appConfig, _communicationService)
                }
            }
        }
    }

    override fun onDestroy() {
        when (_appConfig.currentCommunicationProtocol) {
            TCP -> stop(_appConfig)
        }
        super.onDestroy()
    }
}