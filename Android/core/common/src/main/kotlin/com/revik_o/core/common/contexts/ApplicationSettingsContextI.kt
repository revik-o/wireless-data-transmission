package com.revik_o.core.common.contexts

import com.revik_o.core.common.AppVersion
import com.revik_o.core.common.CommunicationProtocol

interface ApplicationSettingsContextI {

    val awaitTimeout: Int
        get() = 300

    val applicationVersion: AppVersion
        get() = AppVersion.LATEST_VERSION

    val isCommunicationEnabled: Boolean
        get() = false

    val currentCommunicationProtocol: CommunicationProtocol
        get() = CommunicationProtocol.TCP

    val tcpPort: Int
        get() = 5050

    val deviceName: String

    fun disableCommunication()

    fun enableCommunication()
}