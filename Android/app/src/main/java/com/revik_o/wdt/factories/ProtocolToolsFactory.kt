package com.revik_o.wdt.factories

import com.revik_o.core.common.CommunicationProtocol
import com.revik_o.core.common.exceptions.UnexpectedArgumentException
import com.revik_o.infrastructure.common.CommunicationProtocolFetcherI
import com.revik_o.infrastructure.common.CommunicationProtocolSenderI
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.tcp.TCPFetcher
import com.revik_o.infrastructure.tcp.TCPSender

object ProtocolToolsFactory {

    fun createFetcher(api: OSAPIInterface): CommunicationProtocolFetcherI =
        when (api.appSettings.currentCommunicationProtocol) {
            CommunicationProtocol.TCP -> TCPFetcher(api)
            else -> throw UnexpectedArgumentException(api.appSettings.currentCommunicationProtocol.name)
        }

    fun createSender(api: OSAPIInterface): CommunicationProtocolSenderI =
        when (api.appSettings.currentCommunicationProtocol) {
            CommunicationProtocol.TCP -> TCPSender(api)
            else -> throw UnexpectedArgumentException(api.appSettings.currentCommunicationProtocol.name)
        }
}