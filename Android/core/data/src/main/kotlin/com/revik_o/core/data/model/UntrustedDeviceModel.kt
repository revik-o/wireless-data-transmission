package com.revik_o.core.data.model

import java.util.Date

class UntrustedDeviceModel(
    val id: Long,
    val device: DeviceModel,
    val title: String = device.deviceName,
    val additionDate: Date = Date()
)