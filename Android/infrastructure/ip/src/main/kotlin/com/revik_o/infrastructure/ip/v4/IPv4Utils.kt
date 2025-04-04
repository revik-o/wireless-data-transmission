package com.revik_o.infrastructure.ip.v4

import com.revik_o.core.dto.DeviceInfoDto
import com.revik_o.core.dto.RemoteDeviceDto
import com.revik_o.core.factory.CommunicationContextFactory.buildCommunicationContext
import com.revik_o.core.service.DeviceCommunicationServiceI
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections

fun isIPv4LocalNetwork(ipV4: String): Boolean {
    val ipParts = ipV4.split('.').map { it.toInt() }

    return when (ipParts[0]) {
        10 -> true
        172 -> ipParts[1] in 16..31
        192 -> ipParts[1] == 168
        else -> false
    }
}

fun getIPv4Address(): Iterable<String> {
    val result = ArrayList<String>()
    val interfaces = NetworkInterface.getNetworkInterfaces()

    for (item in interfaces) {
        for (address in item.inetAddresses) {
            if (!address.isLoopbackAddress && address is Inet4Address) {
                val v4Address = address.getHostAddress()

                if (isIPv4LocalNetwork(v4Address)) {
                    result.add(v4Address)
                }
            }
        }
    }

    return Collections.unmodifiableList(result)
}

fun scanNetwork(
    subnet: String,
    communicationService: DeviceCommunicationServiceI,
    onConnect: (RemoteDeviceDto) -> Unit
) {
    for (i in 1..254) {
        val host = "$subnet.$i"
        communicationService.send(buildCommunicationContext(DeviceInfoDto(host)), onConnect)
    }
}

fun scanNetwork(
    communicationService: DeviceCommunicationServiceI,
    onConnect: (RemoteDeviceDto) -> Unit
) {
    for (ipV4 in getIPv4Address()) {
        val subnet = ipV4.substring(0, ipV4.lastIndexOf('.'))
        scanNetwork(subnet, communicationService, onConnect)
    }
}