package com.revik_o.infrastructure.tcp

import com.revik_o.config.ApplicationConfigI
import com.revik_o.config.RemoteControllerI
import com.revik_o.core.AppVersion
import com.revik_o.core.context.ClipboardCommunicationContextI
import com.revik_o.core.context.CommunicationContextI
import com.revik_o.core.context.FileCommunicationContextI
import com.revik_o.core.dto.RemoteDeviceDto
import com.revik_o.core.dto.RemoteDeviceDto.Companion.buildRemoteDeviceDto
import com.revik_o.core.entity.DeviceEntity.Companion.DeviceType
import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType
import com.revik_o.core.exception.DataTransferFailedException
import com.revik_o.core.handler.CommunicationHandlerI
import com.revik_o.core.service.DeviceCommunicationServiceI
import com.revik_o.infrastructure.resource.ResourceUtils
import com.revik_o.infrastructure.tcp.TCPAppCodes.DECLINED_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.OK_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.OP_DONE_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_DEVICE
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_RESOURCE
import com.revik_o.infrastructure.tcp.TCPAppCodes.UNSUPPORTED_VERSION
import com.revik_o.infrastructure.tcp.exception.UnsupportedDeviceTypeException
import com.revik_o.infrastructure.tcp.exception.UnsupportedResourceTypeException
import com.revik_o.infrastructure.tcp.exception.UnsupportedVersionException
import java.net.InetSocketAddress
import java.net.Socket

class TCPCommunicationService(
    private val _appConfigPtr: ApplicationConfigI,
    private val _remoteController: RemoteControllerI
) : DeviceCommunicationServiceI {

    private fun getDeviceSignature(resourceType: ResourceType, appVersion: AppVersion): String =
        "${resourceType.signature}%/%${DeviceType.PHONE.signature}%/%${appVersion.name}%/%${_appConfigPtr.deviceName}"

    private inline fun tcpPrelude(ipV4: String, then: (CommunicationHandlerI) -> Unit) =
        Socket().use { socket ->
            socket.connect(
                InetSocketAddress(ipV4, _appConfigPtr.tcpPort),
                _appConfigPtr.tcpConnectionTimeout
            )
            socket.soTimeout = _appConfigPtr.tcpConnectionTimeout +
                    (_appConfigPtr.tcpConnectionTimeout * 0.1).toInt()
            then(TCPCommunicationHandler(socket))
        }

    private inline fun clientPrelude(
        ipV4: String,
        resourceType: ResourceType,
        appVersion: AppVersion,
        then: (CommunicationHandlerI) -> Unit = {}
    ) = tcpPrelude(ipV4) { handler ->
        handler.send(getDeviceSignature(resourceType, appVersion))

        when (handler.readInt()) {
            UNSUPPORTED_VERSION -> throw UnsupportedVersionException(appVersion)
            UNSUPPORTED_RESOURCE -> throw UnsupportedResourceTypeException(resourceType)
            UNSUPPORTED_DEVICE -> throw UnsupportedDeviceTypeException(DeviceType.PHONE)
        }

        then(handler)
    }

    private inline fun clientPrelude(
        communicationContext: CommunicationContextI,
        then: (CommunicationHandlerI) -> Unit = {}
    ) = clientPrelude(
        communicationContext.communicationDevice.ipV4,
        communicationContext.resourceType,
        communicationContext.applicationVersion,
        then
    )

    override fun send(
        communicationContext: ClipboardCommunicationContextI,
        then: (Boolean) -> Unit
    ) = clientPrelude(communicationContext) { handler ->
        handler.send(communicationContext.data)

        if (handler.readInt() < OK_STATUS) {
            then(false)
            throw DataTransferFailedException("Failed to send the clipboard data")
        }

        then(true)
    }

    override fun send(
        communicationContext: FileCommunicationContextI,
        onSending: (UShort, String) -> Unit,
        then: (String?) -> Unit
    ) {
        if (communicationContext.data.isNotEmpty()) {
            clientPrelude(communicationContext) { handler ->
                var countFiles = 0u
                var countFolders = 0u

                ResourceUtils.scanResources(
                    { countFiles++ },
                    { countFolders++ },
                    *communicationContext.data
                )

                handler.send("$countFiles,$countFolders")

                if (handler.readInt() < OK_STATUS) {
                    then("Failed to send files")
                    throw DataTransferFailedException("Failed to send files")
                } else {
                    ResourceUtils.scanResources(
                        {
                            handler.send("resource%/%${it.path}%/%${it.length()}")
                            handler.send(it) { progress -> onSending(progress, it.name) }

                            if (handler.readInt() < OK_STATUS) {
                                throw DataTransferFailedException()
                            }
                        },
                        { handler.send("dir%/%${it.path}") },
                        *communicationContext.data
                    )
                    handler.send("$OP_DONE_STATUS:OPDone")

                    if (handler.readInt() < OK_STATUS) {
                        then("Smth went wrong")
                        throw DataTransferFailedException("Smth went wrong")
                    } else {
                        then(null)
                    }
                }
            }
        } else {
            then("Empty data")
            throw DataTransferFailedException("Empty data")
        }
    }

    override fun send(
        communicationContext: CommunicationContextI,
        then: (RemoteDeviceDto) -> Unit
    ) = clientPrelude(communicationContext) { handler ->
        then(buildRemoteDeviceDto(handler.readString()))
    }

    private inline fun checkDeviceInfo(
        communicationHandler: CommunicationHandlerI,
        deviceInfo: RemoteDeviceDto,
        onSuccess: () -> Unit
    ) = when {
        (deviceInfo.appVersion != null && deviceInfo.appVersion!!.isSupportedVersion(AppVersion.LATEST_VERSION))
                && deviceInfo.deviceType != null && deviceInfo.resourceType != null -> {
            communicationHandler.send(OK_STATUS)
            onSuccess()
        }

        deviceInfo.resourceType == null -> communicationHandler.send(UNSUPPORTED_RESOURCE)
        deviceInfo.deviceType == null -> communicationHandler.send(UNSUPPORTED_DEVICE)
        else -> communicationHandler.send(UNSUPPORTED_VERSION)
    }

    private fun acceptFile(communicationHandler: CommunicationHandlerI) {
        val filesAndFolders = communicationHandler.readString().split(',')
        val filesSize = filesAndFolders[0].toUInt()
        val foldersSize = filesAndFolders[1].toUInt()

        if (_remoteController.onAcceptResources(filesSize, foldersSize)) {
            communicationHandler.send(OK_STATUS)
            var response: String

            while (communicationHandler.readString().trim()
                    .also { response = it } != "$OP_DONE_STATUS:OPDone"
            ) {
                val resourceSignature = response.split("%/%")
                val type = resourceSignature[0].trim()
                val resourcePath = resourceSignature[1].trim()
                    .replace("../", "/")
                    .replace("./", "/")
                    .replace("///", "/")
                    .replace("//", "/")

                if (type == "dir") {
                    _remoteController.systemCallMkDir(resourcePath)
                } else {
                    val resourceLength = resourceSignature[2].toULong()

                    communicationHandler.readResource(
                        _remoteController.systemCallMkResource(resourcePath),
                        resourceLength
                    ) { progress -> _remoteController.checkProgress(resourcePath, progress) }

                    communicationHandler.send(OK_STATUS)
                }
            }

            communicationHandler.send(OK_STATUS)
        } else {
            communicationHandler.send(DECLINED_STATUS)
        }
    }

    override fun accept(communicationHandler: CommunicationHandlerI) {
        val deviceInfo = buildRemoteDeviceDto(communicationHandler.readString())
        checkDeviceInfo(communicationHandler, deviceInfo) {
            when (deviceInfo.resourceType!!) {
                ResourceType.PING -> communicationHandler.send(
                    getDeviceSignature(
                        ResourceType.PING,
                        AppVersion.LATEST_VERSION
                    )
                )

                ResourceType.FILE_OR_FOLDER -> acceptFile(communicationHandler)
                ResourceType.CLIPBOARD_DATA -> communicationHandler.send(
                    if (_remoteController.onAcceptClipboard(communicationHandler.readString()))
                        OK_STATUS
                    else
                        DECLINED_STATUS
                )
            }
        }
    }
}