package com.revik_o.infrastructure.tcp

import com.revik_o.core.common.AppVersion
import com.revik_o.core.common.OSType
import com.revik_o.infrastructure.common.CommunicationProtocolFetcherI
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.commands.fetch.DeviceInfoCommand
import com.revik_o.infrastructure.common.commands.fetch.RemoteClipboardCommand
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.APP_VERSION_KEY
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.OS_KEY
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.TITLE_KEY
import com.revik_o.infrastructure.tcp.TCPAppCodes.DECLINED_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.EMPTY_RESOURCE
import com.revik_o.infrastructure.tcp.TCPAppCodes.OK_STATUS
import com.revik_o.infrastructure.tcp.TCPDataHandler.Companion.extractData

class TCPFetcher(private val _osAPI: OSAPIInterface<*>) : CommunicationProtocolFetcherI {

    override suspend fun fetch(command: DeviceInfoCommand): RemoteDeviceDto? =
        createEstablishedTCPConnection(command.ip, _osAPI, command) { handler ->
            val remoteDevice = handler.readRemoteDeviceData()
            val os = remoteDevice.extractData(OS_KEY, OSType::class.java)
            val title = remoteDevice.extractData(TITLE_KEY, String::class.java)
            val appVersion = remoteDevice.extractData(APP_VERSION_KEY, AppVersion::class.java)

            if (os != null && title != null && appVersion != null) {
                handler.send(OK_STATUS)
                RemoteDeviceDto(command.ip, title, os, appVersion)
            } else {
                handler.send(EMPTY_RESOURCE)
                null
            }
        }

    override suspend fun fetch(command: RemoteClipboardCommand): String? =
        createEstablishedTCPConnection(command.ip, _osAPI, command) { handler ->
            when (handler.readInt()) {
                DECLINED_STATUS -> null
                else -> handler.readString()
            }
        }
}