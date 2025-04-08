package com.revik_o.core.common

import java.io.Serializable
import kotlin.enums.enumEntries

enum class RequestType(val signature: Short) : Serializable {
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