package com.revik_o.test

import com.revik_o.core.common.AppVersion
import com.revik_o.core.common.CommunicationProtocol
import com.revik_o.core.common.contexts.ApplicationSettingsContextI

data class ApplicationTestSettings(
    override var deviceName: String = "JUnit test device :D",
    override var applicationVersion: AppVersion = AppVersion.LATEST_VERSION,
    override var currentCommunicationProtocol: CommunicationProtocol = CommunicationProtocol.TCP,
    override val awaitTimeout: Int = 1000
) : ApplicationSettingsContextI {

    @Volatile
    private var _communicationEnabled = true
        @Synchronized set

    override val isCommunicationEnabled: Boolean
        get() = _communicationEnabled

    override fun disableCommunication() {
        _communicationEnabled = false
    }

    override fun enableCommunication() {
        _communicationEnabled = true
    }
}