package com.revik_o.core.data.model

import java.util.Date

data class DeviceModel(
    val id: Long,
    val deviceName: String,
    val operatingSystem: OS,
    val ipV4Address: String,
    val additionDate: Date = Date()
) {

    enum class OS(val signature: Short) {
        ANDROID(0), LINUX(1), WINDOWS(2)
    }
}
