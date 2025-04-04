package com.revik_o.infrastructure.tcp.exception

import com.revik_o.core.entity.DeviceEntity

open class UnsupportedDeviceTypeException(type: DeviceEntity.Companion.DeviceType) :
    UnsupportedException("The remote device does not support the device ${type.name}")