package com.revik_o.wdt.listeners.button

import android.app.AlertDialog
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.ComponentActivity
import androidx.activity.contextaware.ContextAware
import com.revik_o.core.common.FetchOrSendType
import com.revik_o.core.common.FetchOrSendType.FETCH
import com.revik_o.core.common.FetchOrSendType.SEND
import com.revik_o.core.common.RequestType
import com.revik_o.core.common.utils.ConcurrencyUtils.runAsync
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.commands.fetch.RemoteClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ResourcesCommand
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto
import com.revik_o.wdt.factories.ProtocolToolsFactory.createFetcher
import com.revik_o.wdt.factories.ProtocolToolsFactory.createSender

class DeviceOnClickListener(
    activity: ComponentActivity,
    private val _osApi: OSAPIInterface,
    private val _device: RemoteDeviceDto,
    private val _requestType: RequestType,
    private val _fetchOrSendType: FetchOrSendType,
    private val _resources: Array<out String> = arrayOf()
) : ContextAware by activity, OnClickListener {

    private fun rememberDeviceAlert(than: () -> Unit) =
        (AlertDialog.Builder(peekAvailableContext()).setMessage("Remember this device?")
            .setPositiveButton("Yes") { dialog, _ -> than(); dialog.cancel() }
            .setNegativeButton("No") { dialog, _ -> than(); dialog.cancel() }
            .create() ?: throw IllegalStateException("Activity cannot be null")).show()

    private fun confirmationAlert(than: () -> Unit) =
        (AlertDialog.Builder(peekAvailableContext()).setMessage("You are sure?")
            .setPositiveButton("Yes") { dialog, _ -> than(); dialog.cancel() }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            .create() ?: throw IllegalStateException("Activity cannot be null")).show()

    private suspend fun handleClipboard() {
        when (_fetchOrSendType) {
            SEND -> createSender(_osApi).send(ClipboardCommand(_device.ip))
            FETCH -> createFetcher(_osApi).fetch(RemoteClipboardCommand(_device.ip)).let { data ->
                if (data != null) {
                    _osApi.clipboardService.putDataFromRemoteClipboard(data)
                }
            }
        }
    }

    private suspend fun handleResources() {
        when (_fetchOrSendType) {
            FETCH -> throw IllegalArgumentException()
            SEND -> createSender(_osApi).send(ResourcesCommand(_device.ip, _resources))
        }
    }

    override fun onClick(ignore: View?) {
        confirmationAlert {
            rememberDeviceAlert {
                runAsync {
                    when (_requestType) {
                        RequestType.CLIPBOARD -> handleClipboard()
                        RequestType.RESOURCES -> handleResources()
                        else -> throw IllegalArgumentException()
                    }
                }
            }
        }
    }
}