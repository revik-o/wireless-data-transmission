package com.revik_o.core.entity

import java.util.Date

data class DeviceEntity(
    val id: Long,
    val deviceName: String,
    val deviceType: DeviceType,
    val ipV4Address: String,
    val additionDate: Date = Date()
) {

    enum class DeviceType(val signature: Short) {
        ANDROID_PHONE(0), LINUX_MACHINE(1), WINDOWS_MACHINE(2);

        companion object {
            fun getDeviceTypeBySignature(signature: Short): DeviceType? {
                for (device in DeviceType.entries) {
                    if (signature == device.signature) {
                        return device
                    }
                }

                return null
            }
        }
    }
}
