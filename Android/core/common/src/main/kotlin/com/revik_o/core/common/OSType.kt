package com.revik_o.core.common

import kotlin.enums.enumEntries

enum class OSType(val signature: Short) {

    ANDROID(0), LINUX(1), WINDOWS(2);

    companion object {
        fun getOSBySignature(signature: Short): OSType? {
            for (type in enumEntries<OSType>()) {
                if (signature == type.signature) {
                    return type
                }
            }

            return null
        }
    }
}