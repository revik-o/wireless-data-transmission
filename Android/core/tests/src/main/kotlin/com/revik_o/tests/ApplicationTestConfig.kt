package com.revik_o.tests

import com.revik_o.config.ApplicationConfigI
import com.revik_o.core.CommunicationProtocol

data class ApplicationTestConfig(
    override val currentCommunicationProtocol: CommunicationProtocol,
    override val deviceName: String = "test_device_name",
    override val tcpPort: Int = 5678,
) : ApplicationConfigI {

    private var _communicationEnabled = true

    override val isCommunicationEnabled: Boolean
        get() = _communicationEnabled

    @Synchronized
    override fun enableCommunication(): ApplicationConfigI {
        if (!_communicationEnabled) {
            _communicationEnabled = true
        }

        return this
    }

    @Synchronized
    override fun disableCommunication(): ApplicationConfigI {
        if (_communicationEnabled) {
            _communicationEnabled = false
        }

        return this
    }
}