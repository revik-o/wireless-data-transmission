package com.revik_o.infrastructure.common.utils

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

    private fun getIPAddress(): List<String> {
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

    suspend fun iterateOverNetwork(
        subNet: String,
        deviceIpLastNumber: Int,
        then: suspend (String) -> Unit
    ) {
        (1..254).map { index ->
            if (deviceIpLastNumber != index) {
                then("$subNet.$index")
            }
        }
    }

    suspend fun iterateOverNetwork(then: suspend (String) -> Unit) {
        getIPAddress().map { ipV4 ->
            val subNet = ipV4.substring(0, ipV4.lastIndexOf('.'))
            val lastNumber = ipV4.substring(ipV4.lastIndexOf('.') + 1).toInt()
            iterateOverNetwork(subNet, lastNumber, then)
        }
    }
}