package com.revik_o.infrastructure.common.commands.send

import com.revik_o.core.common.RequestType

data class ClipboardCommand(override val ip: String) : SendCommandI {
    override val requestType = RequestType.CLIPBOARD
}