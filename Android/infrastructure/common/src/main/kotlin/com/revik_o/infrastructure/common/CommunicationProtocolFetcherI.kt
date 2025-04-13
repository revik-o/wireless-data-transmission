package com.revik_o.infrastructure.common

import com.revik_o.infrastructure.common.commands.fetch.DeviceInfoCommand
import com.revik_o.infrastructure.common.commands.fetch.RemoteClipboardCommand
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto

interface CommunicationProtocolFetcherI {
    suspend fun fetch(command: DeviceInfoCommand): RemoteDeviceDto?
    suspend fun fetch(command: RemoteClipboardCommand): String?
}