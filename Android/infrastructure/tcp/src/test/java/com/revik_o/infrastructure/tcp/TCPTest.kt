package com.revik_o.infrastructure.tcp

import com.revik_o.core.AppVersion
import com.revik_o.core.CommunicationProtocol
import com.revik_o.core.dto.DeviceInfoDto
import com.revik_o.core.entity.DeviceEntity.Companion.DeviceType
import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType
import com.revik_o.core.factory.CommunicationContextFactory
import com.revik_o.infrastructure.tcp.exception.UnsupportedVersionException
import com.revik_o.tests.ApplicationTestConfig
import com.revik_o.tests.exception.UnexpectedTestBehaviour
import com.revik_o.tests.utils.AsyncUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.ConnectException


class TCPTest {

    private val _appTestConf = ApplicationTestConfig("test_server", CommunicationProtocol.TCP)
    private val _communicationService = TCPCommunicationService(_appTestConf)

    private fun startTCPServer(communicationService: TCPCommunicationService = _communicationService) { // TODO Before
        TCP.start(_appTestConf, communicationService)
        AsyncUtils.sleep(1000)

        assertTrue(_appTestConf.isCommunicationEnabled)
    }

    @Test
    fun stopTCPServerTest() {
        startTCPServer()
        TCP.stop(_appTestConf)

        assertFalse(_appTestConf.isCommunicationEnabled)
        assertThrows(ConnectException::class.java) {
            TCP.stop(_appTestConf)
        }
    }

    @Test
    fun pingTheDeviceTest() {
        startTCPServer()

        _communicationService.send(
            CommunicationContextFactory.buildCommunicationContext(
                DeviceInfoDto("0.0.0.0")
            )
        ) { remoteDeviceInfo ->
            assertEquals(remoteDeviceInfo.deviceName, _appTestConf.deviceName)
            assertEquals(remoteDeviceInfo.appVersion, AppVersion.LATEST_VERSION)
            assertEquals(remoteDeviceInfo.resourceType, ResourceType.PING)
            assertEquals(remoteDeviceInfo.deviceType, DeviceType.PHONE)
        }

        TCP.stop(_appTestConf)
    }

    @Test
    fun catchUnsupportedVersionExceptionTest() {
        startTCPServer()

        assertThrows(UnsupportedVersionException::class.java) {
            _communicationService.send(
                CommunicationContextFactory.buildCommunicationContext(
                    DeviceInfoDto("0.0.0.0"), AppVersion.V1_0_0
                )
            ) {
                throw UnexpectedTestBehaviour()
            }
        }

        TCP.stop(_appTestConf) // TODO After
    }
}