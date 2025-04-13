package com.revik_o.infrastructure.tcp.exceptions

import com.revik_o.core.common.OSType


open class UnsupportedOsTypeException(type: OSType) :
    UnsupportedException("The remote device does not support the device ${type.name}")