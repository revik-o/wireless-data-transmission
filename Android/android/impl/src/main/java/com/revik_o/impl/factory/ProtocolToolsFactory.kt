package com.revik_o.impl.factory

import android.net.Uri
import com.revik_o.core.common.CommunicationProtocol
import com.revik_o.core.common.exceptions.UnexpectedArgumentException
import com.revik_o.impl.AndroidAPI
import com.revik_o.infrastructure.common.CommunicationProtocolFetcherI
import com.revik_o.infrastructure.common.CommunicationProtocolSenderI
import com.revik_o.infrastructure.tcp.TCPFetcher
import com.revik_o.infrastructure.tcp.TCPSender

object ProtocolToolsFactory {

    fun createFetcher(api: AndroidAPI): CommunicationProtocolFetcherI =
        when (api.appSettings.currentCommunicationProtocol) {
            CommunicationProtocol.TCP -> TCPFetcher(api)
            else -> throw UnexpectedArgumentException(api.appSettings.currentCommunicationProtocol.name)
        }

    fun createSender(api: AndroidAPI): CommunicationProtocolSenderI<Uri> =
        when (api.appSettings.currentCommunicationProtocol) {
            CommunicationProtocol.TCP -> TCPSender(api)
            else -> throw UnexpectedArgumentException(api.appSettings.currentCommunicationProtocol.name)
        }
}