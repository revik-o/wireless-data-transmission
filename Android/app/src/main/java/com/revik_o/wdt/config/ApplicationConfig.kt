package com.revik_o.wdt.config

import com.revik_o.config.ApplicationConfigI
import com.revik_o.core.CommunicationProtocol

class ApplicationConfig(
    override val currentCommunicationProtocol: CommunicationProtocol,
    override val tcpPort: Int,
    override val deviceName: String
) : ApplicationConfigI {

    companion object {

        fun getAppConfig(): ApplicationConfig {
            TODO()
        }
    }
}