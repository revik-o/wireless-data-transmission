package com.revik_o.infrastructure.tcp

import com.revik_o.config.ApplicationConfigI
import com.revik_o.core.AppVersion
import com.revik_o.core.context.ClipboardCommunicationContextI
import com.revik_o.core.context.CommunicationContextI
import com.revik_o.core.context.FileCommunicationContextI
import com.revik_o.core.dto.RemoteDeviceDto
import com.revik_o.core.entity.DeviceEntity.Companion.DeviceType
import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType
import com.revik_o.core.exception.DataTransferFailedException
import com.revik_o.core.handler.CommunicationHandlerI
import com.revik_o.core.service.DeviceCommunicationServiceI
import com.revik_o.infrastructure.tcp.exception.UnsupportedDeviceTypeException
import com.revik_o.infrastructure.tcp.exception.UnsupportedResourceTypeException
import com.revik_o.infrastructure.tcp.exception.UnsupportedVersionException
import java.net.InetSocketAddress
import java.net.Socket

class TCPCommunicationService(private val _appConfigPtr: ApplicationConfigI) :
    DeviceCommunicationServiceI {

    private fun getDeviceSignature(resourceType: ResourceType, appVersion: AppVersion): String =
        "${resourceType.signature}%/%${DeviceType.PHONE.signature}%/%${appVersion.name}%/%${_appConfigPtr.deviceName}"

    private fun tcpPrelude(ipV4: String, then: (CommunicationHandlerI) -> Unit) =
        Socket().use { socket ->
            socket.connect(
                InetSocketAddress(ipV4, _appConfigPtr.tcpPort),
                _appConfigPtr.tcpConnectionTimeout
            )
            socket.soTimeout = _appConfigPtr.tcpConnectionTimeout +
                    (_appConfigPtr.tcpConnectionTimeout * 0.1).toInt()
            then(TCPCommunicationHandler(socket))
        }

    private fun clientPrelude(
        ipV4: String,
        resourceType: ResourceType,
        appVersion: AppVersion,
        then: (CommunicationHandlerI) -> Unit = {}
    ) = tcpPrelude(ipV4) { handler ->
        handler.send(getDeviceSignature(resourceType, appVersion))

        when (handler.readInt()) {
            TCPAppCodes.UNSUPPORTED_VERSION -> throw UnsupportedVersionException(appVersion)
            TCPAppCodes.UNSUPPORTED_RESOURCE -> throw UnsupportedResourceTypeException(resourceType)
            TCPAppCodes.UNSUPPORTED_DEVICE -> throw UnsupportedDeviceTypeException(DeviceType.PHONE)
        }

        then(handler)
    }

    override fun send(communicationContext: ClipboardCommunicationContextI) = clientPrelude(
        communicationContext.communicationDevice.ipV4,
        communicationContext.resourceType,
        communicationContext.applicationVersion
    ) { handler ->
        handler.send(communicationContext.data)

        if (handler.readInt() < 0) {
            throw DataTransferFailedException("Failed to send the clipboard data")
        }
    }


    override fun send(communicationContext: FileCommunicationContextI) {
        TODO("Not yet implemented")
    }

    override fun send(
        communicationContext: CommunicationContextI,
        then: (RemoteDeviceDto) -> Unit
    ) = clientPrelude(
        communicationContext.communicationDevice.ipV4,
        communicationContext.resourceType,
        communicationContext.applicationVersion
    ) { handler ->
        then(RemoteDeviceDto.buildRemoteDeviceDto(handler.readString()))
    }

    override fun accept(communicationHandler: CommunicationHandlerI) {
        val deviceInfo = RemoteDeviceDto.buildRemoteDeviceDto(communicationHandler.readString())

        if (deviceInfo.appVersion != null && deviceInfo.deviceType != null && deviceInfo.resourceType != null) {
            if (deviceInfo.appVersion!!.isSupportedVersion(AppVersion.LATEST_VERSION)) {
                communicationHandler.send(TCPAppCodes.OK_STATUS)

                when (deviceInfo.resourceType!!) {
                    ResourceType.PING -> communicationHandler.send(
                        getDeviceSignature(
                            ResourceType.PING,
                            AppVersion.LATEST_VERSION
                        )
                    )

                    ResourceType.FILE_OR_FOLDER -> TODO()
                    ResourceType.CLIPBOARD_DATA -> TODO()
                }
            } else {
                communicationHandler.send(TCPAppCodes.UNSUPPORTED_VERSION)
            }
        } else if (deviceInfo.appVersion == null) {
            communicationHandler.send(TCPAppCodes.UNSUPPORTED_VERSION)
        } else if (deviceInfo.resourceType == null) {
            communicationHandler.send(TCPAppCodes.UNSUPPORTED_RESOURCE)
        } else {
            communicationHandler.send(TCPAppCodes.UNSUPPORTED_DEVICE)
        }

    }
}