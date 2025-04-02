package com.revik_o.core.entity

import java.util.Date

data class TrustedDeviceEntity(
    val id: Long,
    val device: DeviceEntity,
    val title: String = device.deviceName,
    val additionDate: Date = Date()
)
