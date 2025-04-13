package com.revik_o.core.common

import java.io.Serializable
import kotlin.enums.enumEntries

enum class FetchOrSendType(val signature: Short) : Serializable {
    FETCH(1), SEND(2);

    companion object {
        fun getProtocolBySignature(signature: Short): FetchOrSendType? {
            for (version in enumEntries<FetchOrSendType>()) {
                if (signature == version.signature) {
                    return version
                }
            }

            return null
        }
    }
}