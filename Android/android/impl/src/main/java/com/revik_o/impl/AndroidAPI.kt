package com.revik_o.impl

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Build.MANUFACTURER
import android.os.Build.MODEL
import android.util.Log
import com.revik_o.core.common.CommunicationProtocol
import com.revik_o.core.common.contexts.ApplicationSettingsContextI
import com.revik_o.impl.ApplicationSettings.Companion.APP_SETTINGS_FILE_NAME
import com.revik_o.impl.ApplicationSettings.Companion.COMMUNICATION_PROTOCOL_KEY
import com.revik_o.impl.ApplicationSettings.Companion.COMMUNICATION_STATE_KEY
import com.revik_o.impl.ApplicationSettings.Companion.COMMUNICATION_TCP_PORT_KEY
import com.revik_o.impl.ApplicationSettings.Companion.DEVICE_NAME_KEY
import com.revik_o.impl.ApplicationSettings.Companion.PROTOCOL_AWAIT_TIMEOUT_KEY
import com.revik_o.impl.service.ClipboardService
import com.revik_o.impl.service.DownloadStorageService
import com.revik_o.impl.service.ResourceService
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.services.os.ClipboardServiceI
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import com.revik_o.infrastructure.common.services.os.ResourceServiceI
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Properties


class AndroidAPI(
    context: ContextWrapper,
    override val appSettings: ApplicationSettingsContextI = ApplicationSettings(context),
    override val clipboardService: ClipboardServiceI = ClipboardService(context),
    override val downloadStorageService: DownloadStorageServiceI = DownloadStorageService(context),
    override val resourceService: ResourceServiceI<Uri> = ResourceService(context)
) : OSAPIInterface<Uri> {

    companion object {
        const val APPLICATION_LOG_TAG = "WDT"

        fun isFirstApplicationRun(context: Context): Boolean {
            val appFolder = context.externalCacheDir

            if (appFolder == null || !appFolder.exists()) {
                return true
            }

            val propertiesFile = File(appFolder, APP_SETTINGS_FILE_NAME)

            if (!propertiesFile.exists() || propertiesFile.length() == 0L) {
                return true
            }

            return FileInputStream(propertiesFile).use { settings ->
                Properties().let { properties ->
                    properties.load(settings)
                    properties[COMMUNICATION_STATE_KEY] == null
                            || properties[COMMUNICATION_PROTOCOL_KEY] == null
                            || properties[COMMUNICATION_TCP_PORT_KEY] == null
                }
            }
        }

        fun initApplication(context: Context) {
            val cacheDir = context.externalCacheDir ?: throw IllegalStateException()

            if (cacheDir.exists()) {
                cacheDir.deleteRecursively()
            }

            cacheDir.mkdirs()

            FileOutputStream(File(cacheDir, APP_SETTINGS_FILE_NAME)).use { settings ->
                val properties = Properties()
                properties[COMMUNICATION_STATE_KEY] = true.toString()
                properties[COMMUNICATION_PROTOCOL_KEY] =
                    CommunicationProtocol.TCP.signature.toString()
                properties[COMMUNICATION_TCP_PORT_KEY] = 5050.toString()
                properties[PROTOCOL_AWAIT_TIMEOUT_KEY] = 1000.toString()
                properties[DEVICE_NAME_KEY] = if (MODEL.startsWith(MANUFACTURER, true))
                    MODEL.trim() else "$MANUFACTURER $MODEL".trim()
                properties.store(settings, "app settings")
                Log.i(APPLICATION_LOG_TAG, "The application is initialized")
            }
        }
    }
}