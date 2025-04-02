package com.revik_o.infrastructure.tcp.exception

import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType

open class UnsupportedResourceTypeException(type: ResourceType) :
    UnsupportedException("The remote device does not support the resource type ${type.name}")