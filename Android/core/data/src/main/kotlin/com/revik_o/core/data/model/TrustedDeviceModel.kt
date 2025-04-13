package com.revik_o.core.data.model

import java.util.Date

data class TrustedDeviceModel(
    val id: Long,
    val device: DeviceModel,
    val title: String = device.deviceName,
    val additionDate: Date = Date()
)