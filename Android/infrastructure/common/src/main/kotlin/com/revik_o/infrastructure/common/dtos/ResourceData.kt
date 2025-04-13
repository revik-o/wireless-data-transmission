package com.revik_o.infrastructure.common.dtos

data class ResourceData<T>(val path: String, val size: Long, val ref: T)
