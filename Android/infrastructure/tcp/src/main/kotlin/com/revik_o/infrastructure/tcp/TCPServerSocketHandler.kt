package com.revik_o.infrastructure.tcp

import com.revik_o.core.common.AppVersion.Companion.LATEST_VERSION
import com.revik_o.core.common.FetchOrSendType.FETCH
import com.revik_o.core.common.FetchOrSendType.SEND
import com.revik_o.core.common.RequestType
import com.revik_o.core.common.exceptions.DataTransferFailedException
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.getCurrentDeviceDto
import com.revik_o.infrastructure.common.dtos.RemoteResourceData
import com.revik_o.infrastructure.tcp.TCPAppCodes.BROKEN_REQUEST
import com.revik_o.infrastructure.tcp.TCPAppCodes.DECLINED_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.EMPTY_RESOURCE
import com.revik_o.infrastructure.tcp.TCPAppCodes.OK_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_OS
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_REQUEST
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_VERSION
import com.revik_o.infrastructure.tcp.dtos.TCPRemoteDeviceDto
import com.revik_o.infrastructure.tcp.exceptions.UnsupportedException
import java.net.Socket

class TCPServerSocketHandler(private val _api: OSAPIInterface<*>) {

    private fun sendDeviceInfo(dataHandler: TCPDataHandler<*>) {
        dataHandler.send(getCurrentDeviceDto(_api.appSettings))
        if (dataHandler.readInt() < 0) {
            throw DataTransferFailedException()
        }
    }

    private fun throwDeclinedStatus(dataHandler: TCPDataHandler<*>) {
        dataHandler.send(DECLINED_STATUS)
        throw DataTransferFailedException()
    }

    private fun sendResources(dataHandler: TCPDataHandler<*>, device: TCPRemoteDeviceDto): Nothing =
        throw UnsupportedException() // TODO

    private fun acceptResources(dataHandler: TCPDataHandler<*>, device: TCPRemoteDeviceDto) {
        val resourcesCount = dataHandler.readString().toInt()
        val service = _api.downloadStorageService.isServicePermitted(device.title, resourcesCount)

        if (service != null) {
            var result: String
            dataHandler.send(OK_STATUS)

            while (dataHandler.readString().also { result = it } != "EOS") {
                val resourceData = parseToResourceData(result)

                when {
                    resourceData != null -> dataHandler.readResource(
                        service.createResourceOutputStream(resourceData),
                        resourceData.size,
                        onOk = { dataHandler.send(OK_STATUS) },
                        onDeclined = { throwDeclinedStatus(dataHandler) }
                    )

                    else -> throwDeclinedStatus(dataHandler)
                }

                dataHandler.send(OK_STATUS)
            }

            dataHandler.send(OK_STATUS)
        } else {
            dataHandler.send(DECLINED_STATUS)
        }
    }

    private fun sendClipboard(dataHandler: TCPDataHandler<*>, device: TCPRemoteDeviceDto) {
        val service = _api.clipboardService.isServicePermitted(device.title)

        if (service != null) {
            val result = service.clipboardTextData

            if (result != null) {
                dataHandler.send(OK_STATUS)
                dataHandler.send(result)
            } else {
                dataHandler.send(DECLINED_STATUS)
            }
        } else {
            dataHandler.send(DECLINED_STATUS)
        }
    }

    private fun acceptClipboard(dataHandler: TCPDataHandler<*>, device: TCPRemoteDeviceDto) {
        val service = _api.clipboardService.isServicePermitted(device.title)

        if (service != null) {
            dataHandler.send(OK_STATUS)

            if (dataHandler.readInt() >= 0 && service.putDataFromRemoteClipboard(dataHandler.readString())) {
                dataHandler.send(OK_STATUS)
            } else {
                dataHandler.send(DECLINED_STATUS)
            }
        } else {
            dataHandler.send(DECLINED_STATUS)
        }
    }

    private fun startWorkWithRemoteDevice(handler: TCPDataHandler<*>, device: TCPRemoteDeviceDto) {
        val fetchOrSend = device.fetchOrSendType!!
        handler.send(OK_STATUS)

        when (device.requestType!!) {
            RequestType.DEVICE_INFO -> sendDeviceInfo(handler)
            RequestType.RESOURCES -> when (fetchOrSend) {
                SEND -> acceptResources(handler, device)
                FETCH -> sendResources(handler, device)
            }

            RequestType.CLIPBOARD -> when (fetchOrSend) {
                SEND -> acceptClipboard(handler, device)
                FETCH -> sendClipboard(handler, device)
            }
        }
    }

    private fun checkDeviceInfo(deviceInfo: TCPRemoteDeviceDto): Int = when {
        deviceInfo.appVersion == null || !deviceInfo.appVersion.isSupportedVersion(LATEST_VERSION) -> UNSUPPORTED_VERSION
        deviceInfo.os == null -> UNSUPPORTED_OS
        deviceInfo.requestType == null -> UNSUPPORTED_REQUEST
        deviceInfo.fetchOrSendType == null -> BROKEN_REQUEST
        else -> OK_STATUS
    }

    fun accept(socket: Socket) {
        val dataHandler = TCPDataHandler(socket, _api)
        val deviceInfo = dataHandler.readIntent()

        if (deviceInfo != null) {
            when (val validationResult = checkDeviceInfo(deviceInfo)) {
                OK_STATUS -> startWorkWithRemoteDevice(dataHandler, deviceInfo)
                else -> dataHandler.send(validationResult)
            }
        } else {
            dataHandler.send(EMPTY_RESOURCE)
        }
    }
}

private fun parseToResourceData(tcpRawData: String): RemoteResourceData? =
    tcpRawData.split(TCPDataHandler.SPLITTER).let { data ->
        if (data.size >= 2) {
            return RemoteResourceData(data[0], data[1].toLong())
        }

        return null
    }