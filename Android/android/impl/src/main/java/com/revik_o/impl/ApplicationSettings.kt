package com.revik_o.impl

import android.content.Context
import com.revik_o.core.common.contexts.ApplicationSettingsContextI
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties

private const val DEFAULT_PROTOCOL_AWAIT_TIMEOUT = 1000
private const val DEFAULT_TCP_PORT = 5050

class ApplicationSettings(private val _context: Context) : ApplicationSettingsContextI {

    private fun syncWithPropertiesFile(then: (Properties) -> Unit) {
        val cacheDir = _context.externalCacheDir ?: throw IllegalStateException()
        val settingsProperties = File(cacheDir, APP_SETTINGS_FILE_NAME)
        FileInputStream(settingsProperties).use { settings ->
            val properties = Properties()
            properties.load(settings)
            then(properties)
            FileOutputStream(settingsProperties).use { outputStream ->
                properties.store(outputStream, null)
            }
        }
    }

    @Volatile
    private var _communicationState: Boolean = true
        @Synchronized set(value) {
            syncWithPropertiesFile { properties ->
                properties[COMMUNICATION_STATE_KEY] = value.toString()
            }
            field = value
        }

    @Volatile
    private var _awaitTimeout: Int = DEFAULT_PROTOCOL_AWAIT_TIMEOUT
        @Synchronized set(value) {
            syncWithPropertiesFile { properties ->
                properties[PROTOCOL_AWAIT_TIMEOUT_KEY] = value.toString()
            }
            field = value
        }

    @Volatile
    private var _tcpPort: Int = DEFAULT_TCP_PORT
        @Synchronized set(value) {
            syncWithPropertiesFile { properties ->
                properties[COMMUNICATION_TCP_PORT_KEY] = value.toString()
            }
            field = value
        }

    override val awaitTimeout: Int
        get() = _awaitTimeout

    override val isCommunicationEnabled: Boolean
        get() = _communicationState

    override val deviceName: String

    init {
        val cacheDir = _context.externalCacheDir ?: throw IllegalStateException()
        FileInputStream(File(cacheDir, APP_SETTINGS_FILE_NAME)).use { settings ->
            val properties = Properties()
            properties.load(settings)
            this.deviceName = (properties[DEVICE_NAME_KEY] ?: throw IllegalStateException())
                .toString()
            _tcpPort = (properties[COMMUNICATION_TCP_PORT_KEY] ?: throw IllegalStateException())
                .toString().toInt()
            _communicationState =
                (properties[COMMUNICATION_STATE_KEY] ?: throw IllegalStateException())
                    .toString().toBoolean()
            _awaitTimeout =
                (properties[PROTOCOL_AWAIT_TIMEOUT_KEY] ?: throw IllegalStateException())
                    .toString().toInt()
        }
    }

    override fun disableCommunication() {
        if (_communicationState) {
            _communicationState = false
        }
    }

    override fun enableCommunication() {
        if (!_communicationState) {
            _communicationState = true
        }
    }

    companion object {
        const val APP_SETTINGS_FILE_NAME = "settings.properties"

        // Property Keys
        const val COMMUNICATION_STATE_KEY = "communication.state"
        const val COMMUNICATION_PROTOCOL_KEY = "communication.protocol.sig"
        const val COMMUNICATION_TCP_PORT_KEY = "communication.protocol.tcp.port"
        const val PROTOCOL_AWAIT_TIMEOUT_KEY = "communication.protocol.await.timeout"
        const val DEVICE_NAME_KEY = "device.name"
        // End of Property Keys
    }
}