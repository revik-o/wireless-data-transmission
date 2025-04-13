package com.revik_o.infrastructure.common.commands

import com.revik_o.core.common.FetchOrSendType
import com.revik_o.core.common.RequestType

interface NetworkCommandI {
    val ip: String
    val requestType: RequestType
    val fetchOrSendType: FetchOrSendType
}