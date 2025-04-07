package com.revik_o.infrastructure.tcp.exception

import com.revik_o.core.common.RequestType

open class UnsupportedRequestTypeException(type: RequestType) :
    UnsupportedException("The remote device does not support the request type ${type.name}")