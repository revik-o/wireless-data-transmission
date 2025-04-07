package com.revik_o.core.dto

import com.revik_o.core.AppVersion
import com.revik_o.core.entity.DeviceEntity.DeviceType
import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType

data class RemoteDeviceDto(
    val resourceType: ResourceType?,
    val deviceType: DeviceType?,
    val appVersion: AppVersion?,
    val deviceName: String
) {

    companion object {
        fun buildRemoteDeviceDto(rawRemoteDeviceInfo: String): RemoteDeviceDto {
            val splitInfo = rawRemoteDeviceInfo.split("%/%")
            val resourceType = ResourceType.getResourceTypeBySignature(splitInfo[0].toShort())
            val deviceType = DeviceType.getDeviceTypeBySignature(splitInfo[1].toShort())
            val appVersion = AppVersion.getAppVersionBySignature(splitInfo[2])
            val deviceName = splitInfo[3]
            return RemoteDeviceDto(resourceType, deviceType, appVersion, deviceName)
        }
    }
}