package com.revik_o.infrastructure.ip.v4

import com.revik_o.tests.service.MockDeviceCommunicationService
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IPv4UtilsTest {

    @Test
    fun getIPv4AddressTest() {
        for (item in getIPv4Address()) {
            assertFalse(item.contains(":"))
            assertFalse(item.startsWith("127."))
            assertFalse(item.startsWith("0.0.0.0"))
        }
    }

    @Test
    fun scanNetworkTest() {
        var count = 0
        val communicationService = MockDeviceCommunicationService(sendPing = { _, _ -> count++ })

        scanNetwork(communicationService) {
            // ignore
        }

        assertTrue(count > 1)
    }
}