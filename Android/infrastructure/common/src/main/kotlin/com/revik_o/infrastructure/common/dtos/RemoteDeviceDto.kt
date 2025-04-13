package com.revik_o.infrastructure.common.dtos

import com.revik_o.core.common.AppVersion
import com.revik_o.core.common.OSType
import com.revik_o.core.common.contexts.ApplicationSettingsContextI

data class RemoteDeviceDto(
    val ip: String,
    val title: String,
    val os: OSType,
    val appVersion: AppVersion,
) {

    data class CurrentDeviceDto(
        val title: String,
        val os: OSType,
        val appVersion: AppVersion,
    ) {

        companion object {
            const val OS_KEY = "OS"
            const val TITLE_KEY = "TITLE"
            const val APP_VERSION_KEY = "APP_VERSION"

            fun getCurrentDeviceDto(appSettings: ApplicationSettingsContextI): CurrentDeviceDto =
                CurrentDeviceDto(appSettings.deviceName, OSType.ANDROID, AppVersion.LATEST_VERSION)
        }
    }
}
