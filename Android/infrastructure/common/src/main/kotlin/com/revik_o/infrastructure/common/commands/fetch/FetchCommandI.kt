package com.revik_o.infrastructure.common.commands.fetch

import com.revik_o.core.common.FetchOrSendType
import com.revik_o.infrastructure.common.commands.NetworkCommandI

interface FetchCommandI : NetworkCommandI {
    override val fetchOrSendType: FetchOrSendType
        get() = FetchOrSendType.FETCH
}