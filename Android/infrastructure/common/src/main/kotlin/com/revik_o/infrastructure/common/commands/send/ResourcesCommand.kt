package com.revik_o.infrastructure.common.commands.send

import com.revik_o.core.common.RequestType

data class ResourcesCommand(override val ip: String, val paths: Array<out String>) :
    SendCommandI {
    override val requestType = RequestType.RESOURCES
}
