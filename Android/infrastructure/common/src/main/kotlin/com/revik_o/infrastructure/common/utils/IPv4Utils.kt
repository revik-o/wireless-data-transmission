package com.revik_o.infrastructure.common.utils

import com.revik_o.core.common.utils.ConcurrencyUtils.runConcurrentIOOperation
import com.revik_o.infrastructure.common.CommunicationProtocolFetcherI
import com.revik_o.infrastructure.common.commands.fetch.DeviceInfoCommand
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections

object IPv4Utils {

    private fun isLocalNetwork(ipV4: String): Boolean {
        val ipParts = ipV4.split('.').map { it.toInt() }

        return when (ipParts[0]) {
            10 -> true
            172 -> ipParts[1] in 16..31
            192 -> ipParts[1] == 168
            else -> false
        }
    }

    private fun getIPAddress(): Iterable<String> {
        val result = ArrayList<String>()
        val interfaces = NetworkInterface.getNetworkInterfaces()

        for (item in interfaces) {
            for (address in item.inetAddresses) {
                if (!address.isLoopbackAddress && address is Inet4Address) {
                    val v4Address = address.getHostAddress()

                    if (isLocalNetwork(v4Address)) {
                        result.add(v4Address)
                    }
                }
            }
        }

        return Collections.unmodifiableList(result)
    }

    fun scanNetwork(
        subNet: String,
        fetcher: CommunicationProtocolFetcherI,
        then: (RemoteDeviceDto) -> Unit
    ) = runConcurrentIOOperation {
        for (i in 1..254) {
            val device = fetcher.fetch(DeviceInfoCommand("$subNet.$i"))

            if (device != null) {
                then(device)
            }
        }
    }


    fun scanNetwork(fetcher: CommunicationProtocolFetcherI, then: (RemoteDeviceDto) -> Unit) {
        for (ipV4 in getIPAddress()) {
            scanNetwork(ipV4.substring(0, ipV4.lastIndexOf('.')), fetcher, then)
        }
    }
}