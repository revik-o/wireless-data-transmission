package com.revik_o.core.common

import kotlin.enums.enumEntries

enum class RequestType(val signature: Short) {
    DEVICE_INFO(0), RESOURCES(1), CLIPBOARD(2);

    companion object {
        fun getRequestTypeBySignature(signature: Short): RequestType? {
            for (type in enumEntries<RequestType>()) {
                if (signature == type.signature) {
                    return type
                }
            }

            return null
        }
    }
}