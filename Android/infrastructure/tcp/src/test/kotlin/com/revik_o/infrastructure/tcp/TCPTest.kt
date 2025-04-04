package com.revik_o.infrastructure.tcp

import com.revik_o.core.AppVersion
import com.revik_o.core.CommunicationProtocol
import com.revik_o.core.dto.DeviceInfoDto
import com.revik_o.core.entity.DeviceEntity.Companion.DeviceType
import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType
import com.revik_o.core.factory.CommunicationContextFactory
import com.revik_o.infrastructure.resource.ResourceUtils
import com.revik_o.infrastructure.tcp.exception.UnsupportedVersionException
import com.revik_o.tests.ApplicationTestConfig
import com.revik_o.tests.MockRemoteController
import com.revik_o.tests.exception.UnexpectedTestBehaviour
import com.revik_o.tests.utils.AsyncUtils
import com.revik_o.tests.utils.LogUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.net.ConnectException


class TCPTest {

    private val _remoteController = MockRemoteController()
    private val _appTestConf = ApplicationTestConfig(CommunicationProtocol.TCP)
    private val _communicationService = TCPCommunicationService(_appTestConf, _remoteController)

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
    fun sendClipboardTest() {
        startTCPServer()

        val clipboardData = "some clipboard data"
        _remoteController.onAcceptClipboardFunc = { data ->
            assertEquals(clipboardData, data)
            true
        }
        _communicationService.send(
            CommunicationContextFactory.buildCommunicationContext(
                DeviceInfoDto("0.0.0.0"), clipboardData
            )
        ) { result: Boolean ->
            assertTrue(result)
        }

        TCP.stop(_appTestConf)
    }

    @Test
    fun sendFolderTest() {
        val target = "gradle"
        val testPrefix = "./../.."

        startTCPServer()

        _remoteController.onAcceptResourcesFunc = { files, folders ->
            LogUtils.debug("files: $files, folders: $folders")
            true
        }
        _remoteController.checkProgressFunc = { name, progress ->
            LogUtils.debug("server side: name: $name, progress: \u001B[32m$progress%\u001B[0m")
        }
        _communicationService.send(
            CommunicationContextFactory.buildCommunicationContext(
                DeviceInfoDto("0.0.0.0"), File("$testPrefix/$target")
            ),
            onSending = { progress, resource ->
                LogUtils.debug("$resource -> \u001B[32m$progress%\u001B[0m")
            }
        ) { assertNull(it) }

        ResourceUtils.scanResources(onFile = { file ->
            val filePath = file.path;
            val remoteControllerOutputDir = _remoteController.outputDirPath
            val rootFilePath = filePath.substring(
                filePath.lastIndexOf(remoteControllerOutputDir) + remoteControllerOutputDir.length
            )
            val testResource = File("$testPrefix/$rootFilePath")
            assertTrue(testResource.isFile == file.isFile)
            assertEquals(testResource.readText(), file.readText())
            assertEquals(testResource.readText().length, file.readText().length)
        }, onFolder = { dir ->
            val dirPath = dir.path;
            val remoteControllerOutputDir = _remoteController.outputDirPath
            val rootDirPath = dirPath.substring(
                dirPath.lastIndexOf(remoteControllerOutputDir) + remoteControllerOutputDir.length
            )
            assertTrue(File("$testPrefix/$rootDirPath").isDirectory == dir.isDirectory)
        }, File(_remoteController.outputDirPath))

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