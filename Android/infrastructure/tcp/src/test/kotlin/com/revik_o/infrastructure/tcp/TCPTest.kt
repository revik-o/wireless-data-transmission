package com.revik_o.infrastructure.tcp

import com.revik_o.core.common.CommunicationProtocol
import com.revik_o.core.common.utils.ConcurrencyUtils
import com.revik_o.core.common.utils.sleep
import com.revik_o.infrastructure.common.commands.fetch.DeviceInfoCommand
import com.revik_o.infrastructure.common.commands.fetch.RemoteClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ResourcesCommand
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.getCurrentDeviceDto
import com.revik_o.infrastructure.common.utils.IPv4Utils
import com.revik_o.test.TestOSAPI
import com.revik_o.test.service.os.TEST_OUTPUT_DIR
import com.revik_o.test.utils.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.net.ConnectException
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

private const val IP = "0.0.0.0"

class TCPTest {

    private val _mockOSAPI = TestOSAPI()
    private val _fetcher = TCPFetcher(_mockOSAPI)
    private val _sender = TCPSender(_mockOSAPI)


    @Before
    fun before() {
        TCP.start(_mockOSAPI)
        ConcurrencyUtils.sleep(300)
        assertTrue(_mockOSAPI.appSettings.isCommunicationEnabled)
        assertEquals(_mockOSAPI.appSettings.currentCommunicationProtocol, CommunicationProtocol.TCP)
    }

    @After
    fun after() {
        TCP.stop(_mockOSAPI)
        assertFalse(_mockOSAPI.appSettings.isCommunicationEnabled)
        ConcurrencyUtils.sleep(300)
        assertThrows(ConnectException::class.java) {
            TCP.stop(_mockOSAPI)
        }
    }

    @Test
    fun pingTheDeviceTest() = runTest {
        val result = _fetcher.fetch(DeviceInfoCommand(IP))
        val info = getCurrentDeviceDto(_mockOSAPI.appSettings)

        assertNotNull(result)
        assertEquals(result!!.os, info.os)
        assertEquals(result.title, info.title)
        assertEquals(result.appVersion, info.appVersion)
        assertEquals(result.ip, IP)
    }

    @Test
    fun acceptClipboardTest() = runTest {
        val clipboardData = "some clipboard data"
        assertTrue(_mockOSAPI.clipboardService.putDataFromRemoteClipboard(clipboardData))
        val result = _fetcher.fetch(RemoteClipboardCommand(IP))
        assertNotNull(result)
        assertEquals(clipboardData, result)
    }

    @Test
    fun sendClipboardTest() = runTest {
        val clipboardData = "some clipboard data"
        assertTrue(_mockOSAPI.clipboardService.putDataFromRemoteClipboard(clipboardData))
        _mockOSAPI.clipboardService.putData = { data ->
            assertEquals(data, clipboardData)
            true
        }
        _sender.send(ClipboardCommand(IP))
    }

    @Test
    fun sendFolderTest() = runTest {
        val target = "gradle"
        val testPrefix = "./../.."

        _sender.send(ResourcesCommand(IP, arrayOf("$testPrefix/$target")))
        delay(300)

        _mockOSAPI.resourceService.scanDirectories(onResource = { path, length ->
            val file = File(path)
            val rootFilePath = path.substring(
                path.lastIndexOf(TEST_OUTPUT_DIR) + TEST_OUTPUT_DIR.length
            )
            val testResource = File(testPrefix, rootFilePath)
            assertEquals(testResource.length(), length)
            assertEquals(testResource.readText(), file.readText())
            assertEquals(testResource.readText().length, file.readText().length)
        }, onDir = { path ->
            val dir = File(path)
            val rootDirPath = path.substring(
                path.lastIndexOf(TEST_OUTPUT_DIR) + TEST_OUTPUT_DIR.length
            )
            assertTrue(File(testPrefix, rootDirPath).isDirectory == dir.isDirectory)
        }, TEST_OUTPUT_DIR)
    }

    @Test // for connecting JVM to some device (open ports)
    fun runServer() {
        val lock = ReentrantLock()

        lock.withLock {
            LogUtils.debug("TCP Server started...")
            runTest {
                IPv4Utils.iterateOverNetwork { ip ->
                    LogUtils.debug(ip)
                    try {
                        LogUtils.debug(_fetcher.fetch(DeviceInfoCommand(ip))?.title + "!!!!!")
                    } catch (e: Exception) {
                        LogUtils.debug(e.message)
                    }
                }
            }
            lock.newCondition().await()
        }
    }
}