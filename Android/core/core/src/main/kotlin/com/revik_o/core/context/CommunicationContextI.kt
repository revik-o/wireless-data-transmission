package com.revik_o.core.context

import com.revik_o.core.AppVersion
import com.revik_o.core.dto.DeviceInfoDto
import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType

interface CommunicationContextI {

    val applicationVersion: AppVersion
        get() = AppVersion.LATEST_VERSION
    val resourceType: ResourceType
        get() = ResourceType.PING
    val communicationDevice: DeviceInfoDto
}