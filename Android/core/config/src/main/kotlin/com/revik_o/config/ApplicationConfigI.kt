package com.revik_o.config

import com.revik_o.core.CommunicationProtocol

interface ApplicationConfigI {

    val isCommunicationEnabled: Boolean
        get() = true
    val currentCommunicationProtocol: CommunicationProtocol
    val tcpPort: Int
    val tcpConnectionTimeout: Int
        get() = 100
    val deviceName: String

    fun disableCommunication(): ApplicationConfigI = this

    fun enableCommunication(): ApplicationConfigI = this
}