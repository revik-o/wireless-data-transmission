package com.revik_o.infrastructure.tcp

import com.revik_o.core.common.utils.ConcurrencyUtils.concurrencyScope
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.commands.NetworkCommandI
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.getCurrentDeviceDto
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_OS
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_REQUEST
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_VERSION
import com.revik_o.infrastructure.tcp.exceptions.UnsupportedOsTypeException
import com.revik_o.infrastructure.tcp.exceptions.UnsupportedRequestTypeException
import com.revik_o.infrastructure.tcp.exceptions.UnsupportedVersionException
import java.net.InetSocketAddress
import java.net.Socket

fun configureTCPSocket(ip: String, port: Int, timeout: Int): Socket =
    Socket().also { socket ->
        socket.connect(InetSocketAddress(ip, port), timeout)
        socket.soTimeout = timeout + (timeout * 0.5).toInt()
    }

fun <R> sendIntent(
    socket: Socket,
    osAPI: OSAPIInterface<R>,
    command: NetworkCommandI
): TCPDataHandler<R> {
    val handler = TCPDataHandler(socket, osAPI)
    val info = getCurrentDeviceDto(osAPI.appSettings)
    handler.intent(info, command)

    return when (handler.readInt()) {
        UNSUPPORTED_VERSION -> throw UnsupportedVersionException(info.appVersion)
        UNSUPPORTED_OS -> throw UnsupportedOsTypeException(info.os)
        UNSUPPORTED_REQUEST -> throw UnsupportedRequestTypeException(command.requestType)
        else -> handler
    }
}

suspend fun <T, R> createEstablishedTCPConnection(
    ip: String,
    osAPI: OSAPIInterface<R>,
    command: NetworkCommandI,
    then: suspend (TCPDataHandler<R>) -> T?
): T? = concurrencyScope {
    val socket = configureTCPSocket(ip, osAPI.appSettings.tcpPort, osAPI.appSettings.awaitTimeout)
    val handler = sendIntent(socket, osAPI, command)
    val result = then(handler)
    socket.close()
    result
}