package com.revik_o.core.common

import kotlin.enums.enumEntries


enum class CommunicationProtocol(val signature: Short) {
    TCP(0);

    companion object {
        val DEFAULT_PROTOCOL = TCP

        fun getProtocolBySignature(signature: String): CommunicationProtocol? {
            for (version in enumEntries<CommunicationProtocol>()) {
                if (signature == version.name) {
                    return version
                }
            }

            return null
        }
    }
}