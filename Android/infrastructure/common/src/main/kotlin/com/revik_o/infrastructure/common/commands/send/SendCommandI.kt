package com.revik_o.infrastructure.common.commands.send

import com.revik_o.core.common.FetchOrSendType
import com.revik_o.infrastructure.common.commands.NetworkCommandI

interface SendCommandI : NetworkCommandI {
    override val fetchOrSendType: FetchOrSendType
        get() = FetchOrSendType.SEND
}