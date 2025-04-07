package com.revik_o.infrastructure.tcp.dto

import com.revik_o.core.common.AppVersion
import com.revik_o.core.common.FetchOrSendType
import com.revik_o.core.common.OSType
import com.revik_o.core.common.RequestType
import com.revik_o.infrastructure.tcp.handler.TCPDataHandler

data class TCPRemoteDeviceDto(
    val requestType: RequestType?,
    val title: String,
    val os: OSType?,
    val fetchOrSendType: FetchOrSendType?,
    val appVersion: AppVersion?,
) {

    companion object {
        fun buildRemoteDeviceDto(rawRemoteDeviceInfo: String): TCPRemoteDeviceDto? {
            return try {
                val splitInfo = rawRemoteDeviceInfo.split(TCPDataHandler.SPLITTER)
                val requestType = RequestType.getRequestTypeBySignature(splitInfo[0].toShort())
                val osType = OSType.getOSBySignature(splitInfo[1].toShort())
                val fetchOrSendType = FetchOrSendType.getProtocolBySignature(splitInfo[2].toShort())
                val appVersion = AppVersion.getAppVersionBySignature(splitInfo[3])
                val deviceName = splitInfo[4]
                TCPRemoteDeviceDto(
                    requestType,
                    deviceName,
                    osType,
                    fetchOrSendType,
                    appVersion
                )
            } catch (_: Exception) {
                null
            }
        }
    }
}
