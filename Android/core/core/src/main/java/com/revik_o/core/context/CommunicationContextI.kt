package com.revik_o.core.context

import com.revik_o.core.AppVersion
import com.revik_o.core.AppVersion.V2_0_0
import com.revik_o.core.dto.DeviceInfoDto
import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType

interface CommunicationContextI {

    val applicationVersion: AppVersion
        get() = V2_0_0
    val resourceType: ResourceType
        get() = ResourceType.PING
    val communicationDevice: DeviceInfoDto
}