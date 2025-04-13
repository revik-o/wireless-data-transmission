package com.revik_o.core.data

import com.revik_o.core.data.repository.DeviceModelRepositoryI
import com.revik_o.core.data.repository.HistoryRepositoryI
import com.revik_o.core.data.repository.TrustedDeviceRepositoryI
import com.revik_o.core.data.repository.UntrustedDeviceRepositoryI

data class AppDatabase(
    val deviceRepository: DeviceModelRepositoryI,
    val historyRepository: HistoryRepositoryI,
    val trustedDeviceRepository: TrustedDeviceRepositoryI,
    val untrustedDeviceRepository: UntrustedDeviceRepositoryI,
)