package com.revik_o.infrastructure.common.commands.fetch

import com.revik_o.core.common.RequestType

data class DeviceInfoCommand(override val ip: String) : FetchCommandI {
    override val requestType = RequestType.DEVICE_INFO
}
